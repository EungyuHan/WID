package com.example.wid.controller;

import com.example.wid.configuration.JwtIssuerRequestPostProcessor;
import com.example.wid.configuration.JwtUserRequestPostProcessor;
import com.example.wid.entity.*;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.entity.enums.Role;
import com.example.wid.repository.*;
import com.example.wid.security.JwtUtil;
import com.example.wid.service.CertificateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CertificateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CertificateInfoRepository certificateInfoRepository;
    @Autowired
    private ClassCertificateRepository classCertificateRepository;
    @Autowired
    private CompetitionCertificateRepository competitionCertificateRepository;
    @Autowired
    EncryptInfoRepository encryptInfoRepository;

    private MemberEntity issuerEntity;
    private MemberEntity userEntity;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair();

        PublicKey issuerPublicKey = keyGenerator.getPublic();
        PrivateKey issuerPrivateKey = keyGenerator.getPrivate();

        issuerEntity = MemberEntity.builder()
                .username("issuer")
                .password("issuerpassword")
                .email("issuer@issuer.com")
                .name("issuer")
                .phone("01011111111")
                .role(Role.ROLE_ISSUER)
                .publicKey(Base64.getEncoder().encodeToString(issuerPublicKey.getEncoded()))
                .privateKey(Base64.getEncoder().encodeToString(issuerPrivateKey.getEncoded()))
                .build();
        KeyPair keyGenerator2 = keyPairGenerator.genKeyPair();

        PublicKey userPublicKey = keyGenerator2.getPublic();
        PrivateKey userPrivateKey = keyGenerator2.getPrivate();

        userEntity = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01022222222")
                .role(Role.ROLE_USER)
                .publicKey(Base64.getEncoder().encodeToString(userPublicKey.getEncoded()))
                .privateKey(Base64.getEncoder().encodeToString(userPrivateKey.getEncoded()))
                .build();
        memberRepository.save(issuerEntity);
        memberRepository.save(userEntity);
    }

    @Test
    @DisplayName("인증서 1차 이슈어 서명")
    void signCertificateIssuer() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/certificate/issuer/sign")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedCertificateInfo.getId()))
                .with(JwtIssuerRequestPostProcessor.jwtIssuer(jwtUtil, issuerEntity.getUsername(), issuerEntity.getRole()))
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("인증서 2차 사용자 서명")
    void signCertificateUser() throws Exception {
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
                        .decode(issuerEntity.getPrivateKey().getBytes())
                ));

        byte[] encryptData = encrypt(classCertificate.serializeCertificateForSignature().getBytes(), issuerPrivateKey);
        String encodedSignature = Base64.getEncoder().encodeToString(encryptData);

        CertificateInfoEntity certificateInfo = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .build();
        CertificateInfoEntity savedCertificateInfo = certificateInfoRepository.save(certificateInfo);

        EncryptInfoEntity encryptInfo = EncryptInfoEntity.builder()
                .issuerPublicKey(issuerEntity.getPrivateKey())
                .issuerEncrypt(encodedSignature)
                .userEncrypt(null)
                .certificateInfo(savedCertificateInfo)
                .build();
        EncryptInfoEntity savedEncryptInfo = encryptInfoRepository.save(encryptInfo);

        classCertificate.setCertificateInfo(savedCertificateInfo);
        classCertificateRepository.save(classCertificate);

        mockMvc.perform(MockMvcRequestBuilders.post("/certificate/user/sign")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(savedCertificateInfo.getId()))
                        .with(JwtUserRequestPostProcessor.jwtUser(jwtUtil, userEntity.getUsername(), userEntity.getRole()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("이슈어가 요청받은 인증서 리스트 조회")
    void getCertificateListIssuer() throws Exception {
        CertificateInfoEntity certificateInfoEntity = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .isSigned(false)
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
        userEntity.getUserCertificates().add(savedCertificateInfo);
        issuerEntity.getIssuedCertificates().add(savedCertificateInfo);

        CertificateInfoEntity certificateInfoEntity2 = CertificateInfoEntity.builder()
                .issuer(issuerEntity)
                .user(userEntity)
                .certificateType(CertificateType.COMPETITION_CERTIFICATE)
                .isSigned(false)
                .build();
        CertificateInfoEntity savedCertificateInfo2 = certificateInfoRepository.save(certificateInfoEntity2);
        CompetitionCertificateEntity competitionCertificateEntity = CompetitionCertificateEntity.builder()
                .certificateInfo(savedCertificateInfo2)
                .competitionName("대회")
                .achievement("우수상")
                .organizer("주최기관")
                .summary("블록체인 기반 활동내역 증명 서비스")
                .term("2021.01.01~2021.01.02")
                .build();
        competitionCertificateRepository.save(competitionCertificateEntity);
        userEntity.getUserCertificates().add(savedCertificateInfo2);
        issuerEntity.getIssuedCertificates().add(savedCertificateInfo2);
        memberRepository.save(userEntity);
        memberRepository.save(issuerEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificate/issuer/list")
                        .with(JwtIssuerRequestPostProcessor.jwtIssuer(jwtUtil, issuerEntity.getUsername(), issuerEntity.getRole()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    private byte[] encrypt(byte[] data, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }
}