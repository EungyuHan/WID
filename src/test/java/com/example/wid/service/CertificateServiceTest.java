package com.example.wid.service;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.SignatureInfoEntity;
import com.example.wid.repository.ClassCertificateRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.repository.SignatureInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class CertificateServiceTest {
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClassCertificateRepository classCertificateRepository;
    @Autowired
    private CertificateInfoRepository certificateInfoRepository;
    @Autowired
    private SignatureInfoRepository signatureInfoRepository;

    MemberEntity issuerEntity;
    MemberEntity userEntity;

    String encodedIssuerPublicKey;
    String encodedIssuerPrivateKey;
    String encodedUserPublicKey;
    String encodedUserPrivateKey;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair();

        PublicKey issuerPublicKey = keyGenerator.getPublic();
        PrivateKey issuerPrivateKey = keyGenerator.getPrivate();

        encodedIssuerPublicKey = Base64.getEncoder().encodeToString(issuerPublicKey.getEncoded());
        encodedIssuerPrivateKey = Base64.getEncoder().encodeToString(issuerPrivateKey.getEncoded());
        issuerEntity = MemberEntity.builder()
                .username("issuer")
                .password("issuerpassword")
                .email("issuer@issuer.com")
                .name("issuer")
                .phone("01011111111")
                .publicKey(encodedIssuerPublicKey)
                .build();
        KeyPair keyGenerator2 = keyPairGenerator.genKeyPair();

        PublicKey userPublicKey = keyGenerator2.getPublic();
        PrivateKey userPrivateKey = keyGenerator2.getPrivate();

        encodedUserPublicKey = Base64.getEncoder().encodeToString(userPublicKey.getEncoded());
        encodedUserPrivateKey = Base64.getEncoder().encodeToString(userPrivateKey.getEncoded());
        userEntity = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01022222222")
                .publicKey(encodedUserPublicKey)
                .build();
        memberRepository.save(issuerEntity);
        memberRepository.save(userEntity);
    }
    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
        classCertificateRepository.deleteAll();
        certificateInfoRepository.deleteAll();
        signatureInfoRepository.deleteAll();
    }
    @Test
    @DisplayName("수업 인증서 생성 성공")
    void createClassCertificate() {
        ClassCertificateDTO classCertificateDTO = ClassCertificateDTO.builder()
                .studentId("201911114")
                .subject("소프트웨어공학")
                .professor("김순태")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .startDate("2021-01-01")
                .endDate("2021-01-01")
                .issuerEmail("issuer@issuer.com")
                .file(new MockMultipartFile(
                        "file",
                        "filename.txt",
                        "text/plain",
                        "This is the file content".getBytes()
                ))
                .build();

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());

        assertDoesNotThrow(() -> certificateService.createClassCertificate(classCertificateDTO, userAuthentication));

        assertEquals(1, classCertificateRepository.findAll().size());
        assertEquals(1, certificateInfoRepository.findAll().size());

        ClassCertificateEntity classCertificateEntity = classCertificateRepository.findAll().get(0);
        CertificateInfoEntity certificateInfoEntity = certificateInfoRepository.findAll().get(0);
        assertEquals(classCertificateEntity.getCertificateInfo().getId(), certificateInfoEntity.getId());
    }

    @Test
    @DisplayName("수업 인증서 이슈어 1차 서명 성공")
    void signClassCertificateIssuer() throws NoSuchAlgorithmException {
        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .build();
        certificateInfoRepository.save(certificateInfoEntity);

        ClassCertificateEntity classCertificateEntity = ClassCertificateEntity.builder()
                .certificateInfo(certificateInfoEntity)
                .name("user")
                .studentId("201911114")
                .subject("소프트웨어공학")
                .professor("김순태")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        ClassCertificateEntity savedCertificate = classCertificateRepository.save(classCertificateEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(issuerEntity.getUsername(), issuerEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signClassCertificateIssuer(savedCertificate.getId(), encodedIssuerPrivateKey, authentication));

        assertEquals(1, signatureInfoRepository.findAll().size());
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.findAll().get(0);
        SignatureInfoEntity signatureInfoEntity = signatureInfoRepository.findAll().get(0);

        assertEquals(savedCertificateInfo.getSignatureInfo().getId(), signatureInfoEntity.getId());
    }

    @Test
    @DisplayName("수업 인증서 유저 2차 서명 성공")
    void signClassCertificateUser() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        ClassCertificateEntity classCertificate = ClassCertificateEntity.builder()
                .name("user")
                .studentId("201911114")
                .subject("소프트웨어공학")
                .professor("김순태")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        PrivateKey issuerPrivateKey = KeyFactory
                .getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedIssuerPrivateKey.getBytes())
                ));

        byte[] signData = certificateService.signData(ClassCertificateEntity.serializeClassCertificateForSignature(classCertificate), issuerPrivateKey);
        String encodedSignature = Base64.getEncoder().encodeToString(signData);

        SignatureInfoEntity signatureInfo = SignatureInfoEntity.builder()
                .issuerPublicKey(encodedIssuerPublicKey)
                .issuerSignature(encodedSignature)
                .issuerSignedAt(null)
                .userPublicKey(encodedUserPublicKey)
                .userSignature(null)
                .userSignedAt(null)
                .isUserSigned(false)
                .build();
        SignatureInfoEntity savedSignatureInfo = signatureInfoRepository.save(signatureInfo);

        CertificateInfoEntity certificateInfo = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .signatureInfo(savedSignatureInfo)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfo);

        classCertificate.setCertificateInfo(savedCertificateInfo);
        ClassCertificateEntity savedClassCertificate = classCertificateRepository.save(classCertificate);

        assertEquals(savedCertificateInfo.getSignatureInfo().getId(), savedSignatureInfo.getId());
        assertEquals(savedClassCertificate.getCertificateInfo().getId(), savedCertificateInfo.getId());

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signClassCertificateUser(savedClassCertificate.getId(), encodedUserPrivateKey, userAuthentication));

        SignatureInfoEntity updatedSignatureInfo = signatureInfoRepository.findById(savedSignatureInfo.getId()).get();
        assertNotNull(updatedSignatureInfo.getUserSignature());
        assertNotNull(updatedSignatureInfo.getUserSignedAt());
        assertTrue(updatedSignatureInfo.getIsUserSigned());
    }
}