package com.example.wid.service;

import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.controller.exception.InvalidKeyPairException;
import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.SignatureInfoEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.ClassCertificateRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.repository.SignatureInfoRepository;
import com.example.wid.repository.CertificateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class CertificateService {
    private final MemberRepository memberRepository;
    private final ClassCertificateRepository classCertificateRepository;
    private final CertificateInfoRepository certificateInfoRepository;

    private final SignatureInfoRepository signatureInfoRepository;

    @Autowired
    public CertificateService(MemberRepository memberRepository, ClassCertificateRepository classCertificateRepository, CertificateInfoRepository certificateInfoRepository, SignatureInfoRepository signatureInfoRepository) {
        this.memberRepository = memberRepository;
        this.classCertificateRepository = classCertificateRepository;
        this.certificateInfoRepository = certificateInfoRepository;
        this.signatureInfoRepository = signatureInfoRepository;
    }

    // 수업에 대한 증명서를 생성
    public void createClassCertificate(ClassCertificateDTO classCertificateDTO, Authentication authentication) throws IOException {
        // 사용자 인증서 매핑정보 저장
        MemberEntity issuerEntity = null;
        MemberEntity userEntity = null;

        if (memberRepository.findByEmail(classCertificateDTO.getIssuerEmail()).isPresent()) {
            issuerEntity = memberRepository.findByEmail(classCertificateDTO.getIssuerEmail()).get();
        } else throw new UserNotFoundException("인증서 발급자 정보가 올바르지 않습니다.");

        if (memberRepository.findByUsername(authentication.getName()).isPresent()) {
            userEntity = memberRepository.findByUsername(authentication.getName()).get();
        } else throw new UserNotFoundException("사용자가 존재하지 않습니다.");

        MultipartFile file = classCertificateDTO.getFile();
        String originalFilename = file.getOriginalFilename();
        String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
        // 파일 저장 경로 설정
        // 저장경로 상대경로로 수정 요망
        String savePath = "C:/Users/SAMSUNG/Desktop/wid/src/main/resources/static/" + storedFilename;
        file.transferTo(new File(savePath));

        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .originalFilename(originalFilename)
                .storedFilename(storedFilename)
                .build();

        // 인증서 생성
        ClassCertificateEntity classCertificateEntity = ClassCertificateEntity.builder()
                // 인증 정보 기반
                .name(userEntity.getName())
                // 사용자 입력 정보 기반
                .studentId(classCertificateDTO.getStudentId())
                .subject(classCertificateDTO.getSubject())
                .professor(classCertificateDTO.getProfessor())
                .summary(classCertificateDTO.getSummary())
                .term(classCertificateDTO.getStartDate() + " ~ " + classCertificateDTO.getEndDate())
                .build();

        // 인증서 저장
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfoEntity);
        classCertificateEntity.setCertificateInfo(savedCertificateInfo);
        classCertificateRepository.save(classCertificateEntity);
    }
    // issuer가 서명
    public void signClassCertificateIssuer(Long classCertificateId, String encodedPrivateKey, Authentication authentication){
        try{
            MemberEntity issuer = null;
            if (memberRepository.findByUsername(authentication.getName()).isPresent()) {
                issuer = memberRepository.findByUsername(authentication.getName()).get();
            } else throw new UserNotFoundException("사용자가 존재하지 않습니다.");

            // 인코딩된 개인키와 공개키를 PrivateKey, PublicKey 객체로 변환
            PrivateKey privateKey = KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedPrivateKey.getBytes())
                    ));
            PublicKey publicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(issuer.getPublicKey().getBytes())
                    ));

            if(!verifyKey(publicKey, privateKey)) throw new InvalidKeyPairException();

            // 인증서 정보 확인
            ClassCertificateEntity classCertificateEntity = null;
            if (classCertificateRepository.findById(classCertificateId).isPresent()) {
                classCertificateEntity = classCertificateRepository.findById(classCertificateId).get();
            } else throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");

            // 서명 정보 생성
            String serializedClassCertificate = ClassCertificateEntity.serializeClassCertificateForSignature(classCertificateEntity);
            byte[] signData = signData(serializedClassCertificate, privateKey);

            SignatureInfoEntity signatureInfoEntity = SignatureInfoEntity.builder()
                    .issuerSignature(serializedClassCertificate)
                    .issuerPublicKey(issuer.getPublicKey())
                    .issuerSignedAt(new java.util.Date())
                    .issuerSignature(Base64.getEncoder().encodeToString(signData))
                    .build();

            // 서명 정보 저장
            SignatureInfoEntity savedSignatureInfo = signatureInfoRepository.save(signatureInfoEntity);
            CertificateInfoEntity certificateInfo = classCertificateEntity.getCertificateInfo();
            certificateInfo.setSignatureInfo(savedSignatureInfo);
            certificateInfoRepository.save(certificateInfo);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("서명에 실패하였습니다.");
        }
    }
    
    // 사용자가 서명
    public void signClassCertificateUser(Long classCertificateId, String encodedPrivateKey, Authentication authentication){
        try{
            MemberEntity user = null;
            if (memberRepository.findByUsername(authentication.getName()).isPresent()) {
                user = memberRepository.findByUsername(authentication.getName()).get();
            } else throw new UserNotFoundException("사용자가 존재하지 않습니다.");

            // 인코딩된 개인키와 공개키를 PrivateKey, PublicKey 객체로 변환
            PrivateKey privateKey = KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(encodedPrivateKey.getBytes())
                    ));
            PublicKey publicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(user.getPublicKey().getBytes())
                    ));

            if(!verifyKey(publicKey, privateKey)) throw new InvalidKeyPairException();

            ClassCertificateEntity classCertificateEntity = null;
            if (classCertificateRepository.findById(classCertificateId).isPresent()) {
                classCertificateEntity = classCertificateRepository.findById(classCertificateId).get();
            } else throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");

            // 본인 인증서 맞는지 확인
            CertificateInfoEntity certificateInfo = classCertificateEntity.getCertificateInfo();
            if(certificateInfo.getUser() != user) throw new InvalidCertificateException("인증서에 대한 권한이 없습니다.");
            
            // 올바른 서명정보인지 확인
            SignatureInfoEntity signatureInfo = certificateInfo.getSignatureInfo();
            if(signatureInfo == null) throw new InvalidCertificateException("발급자 서명 완료되지 않았습니다.");
            if(signatureInfo.getIsUserSigned()) throw new InvalidCertificateException("이미 서명하였습니다.");

            String serializedClassCertificate = ClassCertificateEntity.serializeClassCertificateForSignature(classCertificateEntity)
                    + "\n\"issuerSignature\":"
                    + signatureInfo.getIssuerSignature();
            byte[] signData = signData(serializedClassCertificate, privateKey);

            signatureInfo.setUserSignature(Base64.getEncoder().encodeToString(signData));
            signatureInfo.setUserPublicKey(user.getPublicKey());
            signatureInfo.setUserSignedAt(new java.util.Date());
            signatureInfo.setIsUserSigned(true);
            signatureInfoRepository.save(signatureInfo);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("서명에 실패하였습니다.");
        }
    }

    // 클래스 내부 메소드
    // 테스트를 위해 public으로 선언 , 실제 서비스에서는 private로 변경
    public byte[] signData(String data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return signature.sign();
    }

    public boolean verifySignature(String data, byte[] signatureBytes, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(signatureBytes);
    }

    public boolean verifyKey(PublicKey publicKey, PrivateKey privateKey) {
        String testString = "Hello, World!";
        try {
            byte[] signatureBytes = signData(testString, privateKey);
            return verifySignature(testString, signatureBytes, publicKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
