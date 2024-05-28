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
import java.util.Base64;
import java.util.List;

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
    private CertificateInfoRepository certificateInfoRepository;
    @Autowired
    private SignatureInfoRepository signatureInfoRepository;

    @BeforeEach
    void setUp() {
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
        MemberEntity issuerEntity = MemberEntity.builder()
                .username("issuer")
                .password("issuerpassword")
                .email("issuer@issuer.com")
                .name("issuer")
                .phone("01011111111")
                .publicKey("test")
                .build();
        MemberEntity userEntity = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01022222222")
                .publicKey("test2")
                .build();

        memberRepository.save(issuerEntity);
        memberRepository.save(userEntity);

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
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair();

        PublicKey publicKey = keyGenerator.getPublic();
        PrivateKey privateKey = keyGenerator.getPrivate();

        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        MemberEntity issuerEntity = MemberEntity.builder()
                .username("issuer")
                .password("issuerpassword")
                .email("issuer@issuer.com")
                .name("issuer")
                .phone("01011111111")
                .publicKey(encodedPublicKey)
                .build();
        memberRepository.save(issuerEntity);

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
        assertDoesNotThrow(() -> certificateService.signClassCertificateIssuer(savedCertificate.getId(), encodedPrivateKey, authentication));

        assertEquals(1, signatureInfoRepository.findAll().size());
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.findAll().get(0);
        SignatureInfoEntity signatureInfoEntity = signatureInfoRepository.findAll().get(0);

        assertEquals(savedCertificateInfo.getSignatureInfo().getId(), signatureInfoEntity.getId());
    }
}