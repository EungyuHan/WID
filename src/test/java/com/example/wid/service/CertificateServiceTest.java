package com.example.wid.service;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.dto.CompetitionCertificateDTO;
import com.example.wid.entity.*;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.*;
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
    private CompetitionCertificateRepository competitionCertificateRepository;
    @Autowired
    private CertificateInfoRepository certificateInfoRepository;
    @Autowired
    private SignatureInfoRepository signatureInfoRepository;

    private final String ISSUER_SIGNATURE = "\"issuerSignature\" : ";

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
        competitionCertificateRepository.deleteAll();
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

        assertDoesNotThrow(() -> certificateService.createCertificate(classCertificateDTO, userAuthentication, CertificateType.CLASS_CERTIFICATE));

        assertEquals(1, classCertificateRepository.findAll().size());
        assertEquals(1, certificateInfoRepository.findAll().size());

        ClassCertificateEntity classCertificateEntity = classCertificateRepository.findAll().get(0);
        CertificateInfoEntity certificateInfoEntity = certificateInfoRepository.findAll().get(0);
        assertEquals(classCertificateEntity.getCertificateInfo().getId(), certificateInfoEntity.getId());
    }

    @Test
    @DisplayName("대회 인증서 생성 성공")
    void createCompetitionCertificate() {
        CompetitionCertificateDTO competitionCertificateDTO = CompetitionCertificateDTO.builder()
                .competitionName("대회")
                .achievement("우수상")
                .organizer("주최기관")
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

        assertDoesNotThrow(() -> certificateService.createCertificate(competitionCertificateDTO, userAuthentication, CertificateType.COMPETITION_CERTIFICATE));

        assertEquals(1, competitionCertificateRepository.findAll().size());
        assertEquals(1, certificateInfoRepository.findAll().size());

        CompetitionCertificateEntity classCertificateEntity = competitionCertificateRepository.findAll().get(0);
        CertificateInfoEntity certificateInfoEntity = certificateInfoRepository.findAll().get(0);
        assertEquals(classCertificateEntity.getCertificateInfo().getId(), certificateInfoEntity.getId());
    }
    @Test
    @DisplayName("수업 인증서 이슈어 1차 서명 성공")
    void signClassCertificateIssuer(){
        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfoEntity);

        ClassCertificateEntity classCertificateEntity = ClassCertificateEntity.builder()
                .certificateInfo(savedCertificateInfo)
                .name("user")
                .studentId("201911114")
                .subject("소프트웨어공학")
                .professor("김순태")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        ClassCertificateEntity savedClassCertificate = classCertificateRepository.save(classCertificateEntity);

        assertEquals(savedCertificateInfo.getId(), savedClassCertificate.getCertificateInfo().getId());
        assertEquals(savedCertificateInfo.getId(), classCertificateRepository.findByCertificateInfo_Id(savedCertificateInfo.getId()).get().getCertificateInfo().getId());

        Authentication authentication = new UsernamePasswordAuthenticationToken(issuerEntity.getUsername(), issuerEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signCertificateIssuer(savedCertificateInfo.getId(), encodedIssuerPrivateKey, authentication));

        assertEquals(1, signatureInfoRepository.findAll().size());
        CertificateInfoEntity certificateInfo = certificateInfoRepository.findAll().get(0);
        SignatureInfoEntity signatureInfoEntity = signatureInfoRepository.findAll().get(0);

        assertEquals(certificateInfo.getSignatureInfo().getId(), signatureInfoEntity.getId());
    }
    @Test
    @DisplayName("대회 인증서 이슈어 1차 서명 성공")
    void signCompetitionCertificateIssuer(){
        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.COMPETITION_CERTIFICATE)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfoEntity);

        CompetitionCertificateEntity competitionCertificateEntity = CompetitionCertificateEntity.builder()
                .certificateInfo(savedCertificateInfo)
                .competitionName("대회")
                .achievement("우수상")
                .organizer("주최기관")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        CompetitionCertificateEntity savedCompetitionCertificate = competitionCertificateRepository.save(competitionCertificateEntity);

        assertEquals(savedCertificateInfo.getId(), savedCompetitionCertificate.getCertificateInfo().getId());
        assertEquals(savedCertificateInfo.getId(), competitionCertificateRepository.findByCertificateInfo_Id(savedCertificateInfo.getId()).get().getCertificateInfo().getId());

        Authentication authentication = new UsernamePasswordAuthenticationToken(issuerEntity.getUsername(), issuerEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signCertificateIssuer(savedCertificateInfo.getId(), encodedIssuerPrivateKey, authentication));

        assertEquals(1, signatureInfoRepository.findAll().size());
        CertificateInfoEntity certificateInfo = certificateInfoRepository.findAll().get(0);
        SignatureInfoEntity signatureInfoEntity = signatureInfoRepository.findAll().get(0);

        assertEquals(certificateInfo.getSignatureInfo().getId(), signatureInfoEntity.getId());
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

        byte[] signData = certificateService.signData(classCertificate.serializeCertificateForSignature(), issuerPrivateKey);
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
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .signatureInfo(savedSignatureInfo)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfo);

        classCertificate.setCertificateInfo(savedCertificateInfo);
        ClassCertificateEntity savedClassCertificate = classCertificateRepository.save(classCertificate);

        assertEquals(savedCertificateInfo.getSignatureInfo().getId(), savedSignatureInfo.getId());
        assertEquals(savedClassCertificate.getCertificateInfo().getId(), savedCertificateInfo.getId());

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signCertificateUser(savedCertificateInfo.getId(), encodedUserPrivateKey, userAuthentication));

        SignatureInfoEntity updatedSignatureInfo = signatureInfoRepository.findById(savedSignatureInfo.getId()).get();
        assertNotNull(updatedSignatureInfo.getUserSignature());
        assertNotNull(updatedSignatureInfo.getUserSignedAt());
        assertTrue(updatedSignatureInfo.getIsUserSigned());
    }
    @Test
    @DisplayName("대회 인증서 유저 2차 서명 성공")
    void signCompetitionCertificateUser() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        CompetitionCertificateEntity competitionCertificate = CompetitionCertificateEntity.builder()
                .competitionName("대회")
                .achievement("우수상")
                .organizer("주최기관")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        PrivateKey issuerPrivateKey = KeyFactory
                .getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedIssuerPrivateKey.getBytes())
                ));

        byte[] signData = certificateService.signData(competitionCertificate.serializeCertificateForSignature(), issuerPrivateKey);
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
                .certificateType(CertificateType.COMPETITION_CERTIFICATE)
                .signatureInfo(savedSignatureInfo)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfo);

        competitionCertificate.setCertificateInfo(savedCertificateInfo);
        CompetitionCertificateEntity savedCompetitionCertificate = competitionCertificateRepository.save(competitionCertificate);

        assertEquals(savedCertificateInfo.getSignatureInfo().getId(), savedSignatureInfo.getId());
        assertEquals(savedCompetitionCertificate.getCertificateInfo().getId(), savedCertificateInfo.getId());

        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword());
        assertDoesNotThrow(() -> certificateService.signCertificateUser(savedCertificateInfo.getId(), encodedUserPrivateKey, userAuthentication));

        SignatureInfoEntity updatedSignatureInfo = signatureInfoRepository.findById(savedSignatureInfo.getId()).get();
        assertNotNull(updatedSignatureInfo.getUserSignature());
        assertNotNull(updatedSignatureInfo.getUserSignedAt());
        assertTrue(updatedSignatureInfo.getIsUserSigned());
    }
    @Test
    @DisplayName("수업 증명서 검증 성공")
    void verifyCertificate() throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfoEntity);

        ClassCertificateEntity classCertificateEntity = ClassCertificateEntity.builder()
                .certificateInfo(savedCertificateInfo)
                .name("user")
                .studentId("201911114")
                .subject("소프트웨어공학")
                .professor("김순태")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        classCertificateRepository.save(classCertificateEntity);

        String serializedCertificate = classCertificateEntity.serializeCertificateForSignature();
        PrivateKey issuerPrivateKey = KeyFactory
                .getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedIssuerPrivateKey.getBytes())
                ));
        byte[] issuerSignature = certificateService.signData(serializedCertificate, issuerPrivateKey);
        String encodedIssuerSignature = Base64.getEncoder().encodeToString(issuerSignature);

        String serializedCertificateWithIssuerSignature =
                serializedCertificate
                + ISSUER_SIGNATURE
                + "\""
                + encodedIssuerSignature
                + "\"";
        PrivateKey userPrivateKey = KeyFactory
                .getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedUserPrivateKey.getBytes())
                ));
        byte[] userSignature = certificateService.signData(serializedCertificateWithIssuerSignature, userPrivateKey);
        String encodedUserSignature = Base64.getEncoder().encodeToString(userSignature);

        SignatureInfoEntity signatureInfo = SignatureInfoEntity.builder()
                .issuerPublicKey(encodedIssuerPublicKey)
                .issuerSignature(encodedIssuerSignature)
                .issuerSignedAt(null)
                .userPublicKey(encodedUserPublicKey)
                .userSignature(encodedUserSignature)
                .userSignedAt(null)
                .isUserSigned(true)
                .build();
        SignatureInfoEntity savedSignatureInfo = signatureInfoRepository.save(signatureInfo);

        savedCertificateInfo.setSignatureInfo(savedSignatureInfo);
        certificateInfoRepository.save(savedCertificateInfo);

        assertTrue(certificateService.verifyCertificate(savedCertificateInfo.getId()));
    }
}