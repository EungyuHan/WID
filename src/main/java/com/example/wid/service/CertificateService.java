package com.example.wid.service;

import com.example.wid.controller.exception.EncryptionException;
import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.controller.exception.InvalidKeyPairException;
import com.example.wid.controller.exception.UserNotFoundException;
import com.example.wid.dto.SentCertificateDTO;
import com.example.wid.dto.base.BaseCertificateDTO;
import com.example.wid.dto.base.BaseCertificateJson;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.EncryptInfoEntity;
import com.example.wid.entity.FolderEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.SentCertificateEntity;
import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.repository.EncryptInfoRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.service.strategy.CertificateStrategy;
import com.example.wid.util.CertificateStrategyFinder;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class CertificateService {
    private final MemberRepository memberRepository;
    private final CertificateInfoRepository certificateInfoRepository;
    private final EncryptInfoRepository encryptInfoRepository;
    private final CertificateStrategyFinder finder;

    @Autowired
    public CertificateService(MemberRepository memberRepository,
                              CertificateInfoRepository certificateInfoRepository,
                              EncryptInfoRepository encryptInfoRepository
            , List<CertificateStrategy> strategies, CertificateStrategyFinder finder) {
        this.memberRepository = memberRepository;
        this.certificateInfoRepository = certificateInfoRepository;
        this.encryptInfoRepository = encryptInfoRepository;
        this.finder = finder;
    }

    // 증명서 생성
    @Transactional
    public void createCertificate(BaseCertificateDTO certificateDTO, Authentication authentication,
                                  CertificateType certificateType) throws IOException {

        // 사용자 인증서 매핑정보 저장
        // 이슈어 정보 확인
        MemberEntity issuerEntity = memberRepository.findByEmail(certificateDTO.getIssuerEmail())
                .orElseThrow(() -> new UserNotFoundException("인증서 발급자 정보가 올바르지 않습니다."));
        CertificateStrategy certificateStrategy = finder.find(certificateType);
        // 사용자 정보 확인
        MemberEntity userEntity = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 올바르지 않습니다."));

        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(certificateType)
                .isSigned(false)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfoEntity);
        userEntity.getUserCertificates().add(savedCertificateInfo);
        issuerEntity.getIssuedCertificates().add(savedCertificateInfo);
        memberRepository.save(userEntity);
        memberRepository.save(issuerEntity);

        MultipartFile file = certificateDTO.getFile();
        // 파일 저장 경로 설정
        // 저장경로 상대경로로 수정 요망
        String savePath =
                "/Users/son-yeongbin/Fabric_Sample/fabric-samples/asset-transfer-basic/WID2/src/main/resources/static"
                        + certificateDTO.getStoredFilename();
        file.transferTo(new File(savePath));

        BaseCertificateEntity baseCertificateEntity = certificateDTO.toCertificateEntity();
        certificateStrategy.save(baseCertificateEntity, savedCertificateInfo);
    }

    // issuer가 서명
    @Transactional
    public Map<String, String> signCertificateIssuer(Long certificateId, Authentication authentication) {
        try {
            MemberEntity issuer = memberRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

            String issuerPrivateKey = issuer.getPrivateKey();
            String issuerPublicKey = issuer.getPublicKey();

            if (issuerPrivateKey == null || issuerPublicKey == null) {
                throw new InvalidKeyPairException("키가 존재하지 않습니다.");
            }
            // 인코딩된 개인키와 공개키를 PrivateKey, PublicKey 객체로 변환
            PrivateKey privateKey = KeyFactory
                    .getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(issuer.getPrivateKey().getBytes())
                    ));

            // 인증서 정보 가져오기
            CertificateInfoEntity certificateInfo = certificateInfoRepository.findById(certificateId)
                    .orElseThrow(() -> new InvalidCertificateException("인증서 정보가 올바르지 않습니다."));

            PublicKey publicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(certificateInfo.getUser().getPublicKey().getBytes())
                    ));

            // 인증서 정보에 저장된 하위 증명서 가져오기
            CertificateType certificateType = certificateInfo.getCertificateType();
            CertificateStrategy certificateStrategy = finder.find(certificateType);
            BaseCertificateEntity baseCertificateEntity = certificateStrategy.getCertificate(certificateId);
            if (baseCertificateEntity == null) {
                throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");
            }

            // 서명 정보 생성
            String serializedClassCertificate = baseCertificateEntity.serializeCertificateForSignature();
            // 1차 서명
            byte[] encryptData = encrypt(serializedClassCertificate.getBytes(), privateKey);
            // 1차 서명 결과에서 앞에서 20byte를 분할하여 저장
            byte[] removedByte = new byte[20];
            System.arraycopy(encryptData, 0, removedByte, 0, 20);
            certificateInfo.setRemovedByte(Base64.getEncoder().encodeToString(removedByte));
            certificateInfo.setIsSigned(true);
            certificateInfoRepository.save(certificateInfo);

            // 2차 암호화를 위해 20byte를 제외한 데이터만 추출
            byte[] compressedEncryptByte = new byte[encryptData.length - 20];
            System.arraycopy(encryptData, 20, compressedEncryptByte, 0, compressedEncryptByte.length);
            // 2차 암호화
            byte[] reEncryptData = encrypt(compressedEncryptByte, publicKey);
            String encodedUserEncrypt = Base64.getEncoder().encodeToString(reEncryptData);

            String didId = "did" + certificateInfo.getId();
            Map<String, String> certificate = new HashMap<>();
            certificate.put("assetId", didId);
            certificate.put("encryptedstring", encodedUserEncrypt);
            certificate.put("addedstring", certificateInfo.getRemovedByte());
            certificate.put("type", certificateType.toString());

            return certificate;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptionException(e.getMessage());
        }
    }

    // 사용자가 서명
    @Transactional
    public Map<String, String> signCertificateUser(Long certificateId) {
        try {
            CertificateInfoEntity certificateInfo = certificateInfoRepository.findById(certificateId)
                    .orElseThrow(() -> new InvalidCertificateException("인증서 정보가 올바르지 않습니다."));
            MemberEntity user = certificateInfo.getUser();
            PublicKey publicKey = KeyFactory
                    .getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(Base64
                            .getDecoder()
                            .decode(user.getPublicKey().getBytes())
                    ));

            CertificateType certificateType = certificateInfo.getCertificateType();
            CertificateStrategy certificateStrategy = finder.find(certificateType);
            BaseCertificateEntity baseCertificateEntity = certificateStrategy.getCertificate(certificateId);
            if (baseCertificateEntity == null) {
                throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");
            }
            if (certificateInfo.getUser() != user) {
                throw new InvalidCertificateException("인증서에 대한 권한이 없습니다.");
            }

            // 올바른 서명정보인지 확인
            EncryptInfoEntity encryptInfo = encryptInfoRepository.findByCertificateInfoId(certificateId)
                    .orElseThrow(() -> new InvalidCertificateException("서명 정보가 존재하지 않습니다."));

            // 키사이즈 2048byte = 최대 암호화 가능길이 245byte
            // 1차로 암호화(Issuer 암호화) 시 결과 256byte에서 20byte를 분할
            // 분할된 데이터는 저장하고, 나머지 236byte를 다시 암호화
            byte[] issuerEncrypt = Base64.getDecoder().decode(encryptInfo.getIssuerEncrypt());
            byte[] removedByte = new byte[20];
            byte[] compressedEncryptByte = new byte[issuerEncrypt.length - 20];
            // 분할된 20byte를 저장
            System.arraycopy(issuerEncrypt, issuerEncrypt.length - 20, removedByte, 0, 20);
            certificateInfo.setRemovedByte(Base64.getEncoder().encodeToString(removedByte));
            certificateInfo.setIsSigned(true);
            certificateInfoRepository.save(certificateInfo);

            // 나머지 236byte를 암호화
            System.arraycopy(issuerEncrypt, 0, compressedEncryptByte, 0, issuerEncrypt.length - 20);

            byte[] encryptData = encrypt(compressedEncryptByte, publicKey);

            String encodedUserEncrypt = Base64.getEncoder().encodeToString(encryptData);

            // 블록체인 네트워크 연동 후 encryptInfo 삭제하는 코드 추가 필요
            // 블록체인 네트워크에 업로드하는 코드 추가 필요
            String didId = "did" + certificateInfo.getId();
            Map<String, String> certificate = new HashMap<>();
            certificate.put("assetId", didId);
            certificate.put("encryptedstring", encodedUserEncrypt);
            certificate.put("addedstring", certificateInfo.getRemovedByte());
            certificate.put("type", certificateType.toString());
            encryptInfoRepository.delete(encryptInfo);

            return certificate;
        } catch (Exception e) {
            throw new EncryptionException(e.getMessage());
        }
    }

    @Transactional
    public List<BaseCertificateJson> getIssuerCertificates(Authentication authentication) {
        MemberEntity issuer = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        List<CertificateInfoEntity> certificateInfoEntities = issuer.getIssuedCertificates();
        List<BaseCertificateJson> baseCertificateJsons = new ArrayList<>();
        for (CertificateInfoEntity certificateInfo : certificateInfoEntities) {
            if (certificateInfo.getIsSigned() == false && encryptInfoRepository.findByCertificateInfoId(
                    certificateInfo.getId()).isEmpty()) {
                CertificateStrategy certificateStrategy = finder.find(certificateInfo.getCertificateType());
                BaseCertificateJson certificateJson = certificateStrategy.getCertificateJson(certificateInfo);
                baseCertificateJsons.add(certificateJson);
            }
        }
        return baseCertificateJsons;
    }

    @Transactional
    public List<SentCertificateDTO> getVerifierCertificates(Authentication authentication) {
        MemberEntity verifier = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        List<SentCertificateEntity> sentCertificates = verifier.getSentCertificates();
        List<SentCertificateDTO> sentCertificateDTOs = new ArrayList<>();
        for (SentCertificateEntity sentCertificate : sentCertificates) {
            FolderEntity folder = sentCertificate.getFolder();
            SentCertificateDTO sentCertificateDTO = SentCertificateDTO.builder()
                    .sentCertificateId(sentCertificate.getId())
                    .folderId(folder.getId())
                    .userEmail(folder.getUser().getEmail())
                    .folderName(folder.getFolderName())
                    .build();
            sentCertificateDTOs.add(sentCertificateDTO);
        }
        return sentCertificateDTOs;
    }


    // 클래스 내부 메소드
    // 테스트를 위해 public으로 선언 , 실제 서비스에서는 private로 변경
    public byte[] encrypt(byte[] data, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}

