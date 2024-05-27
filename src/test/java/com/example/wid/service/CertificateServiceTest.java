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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class CertificateServiceTest {
    @InjectMocks
    private CertificateService certificateService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ClassCertificateRepository classCertificateRepository;
    @Mock
    private CertificateInfoRepository certificateInfoRepository;
    @Mock
    private SignatureInfoRepository signatureInfoRepository;
    @Test
    void createClassCertificate() {
        // given
        ClassCertificateDTO classCertificateDTO = new ClassCertificateDTO();
        classCertificateDTO.setMajor("Computer Science");
        classCertificateDTO.setSubject("Web Application Development");
        classCertificateDTO.setProfessor("professor");
        classCertificateDTO.setDetail("A+");
        classCertificateDTO.setStartDate("2021-01-01");
        classCertificateDTO.setEndDate("2021-06-01");
        classCertificateDTO.setIssuerEmail("issuer@issuer.com");

        // 임시파일 생성
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "This is the file content".getBytes()
        );
        classCertificateDTO.setFile(mockFile);

        MemberEntity issuerEntity = mock(MemberEntity.class);
        MemberEntity userEntity = mock(MemberEntity.class);

        Authentication userAuthentication = mock(Authentication.class);
        when(userAuthentication.getName()).thenReturn("user");

        when(memberRepository.findByEmail(anyString())).thenReturn(java.util.Optional.of(issuerEntity));
        when(memberRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(userEntity));
        // when
        assertDoesNotThrow(() -> certificateService.createClassCertificate(classCertificateDTO, userAuthentication));
        // then
        verify(certificateInfoRepository, times(1)).save(any(CertificateInfoEntity.class));
        verify(classCertificateRepository, times(1)).save(any(ClassCertificateEntity.class));
    }

    @Test
    void signClassCertificateIssuer() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyGenerator = keyPairGenerator.genKeyPair();

        PublicKey publicKey = keyGenerator.getPublic();
        PrivateKey privateKey = keyGenerator.getPrivate();

        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        MemberEntity issuerEntity = mock(MemberEntity.class);
        when(memberRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(issuerEntity));
        when(issuerEntity.getPublicKey()).thenReturn(encodedPublicKey);

        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity();
        when(classCertificateRepository.findById(1L)).thenReturn(java.util.Optional.of(classCertificateEntity));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");

        assertDoesNotThrow(() -> certificateService.signClassCertificateIssuer(1L, encodedPrivateKey, authentication));
        PrivateKey decodedPrivateKey = KeyFactory
                .getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedPrivateKey.getBytes())
                ));
        PublicKey decodedPublicKey = KeyFactory
                .getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64
                        .getDecoder()
                        .decode(encodedPublicKey.getBytes())
                ));
        assertTrue(certificateService.verifyKey(decodedPublicKey, decodedPrivateKey));
        verify(signatureInfoRepository, times(1)).save(any(SignatureInfoEntity.class));
    }
}