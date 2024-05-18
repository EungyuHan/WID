package com.example.wid.service;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.enums.Role;
import com.example.wid.repository.ClassCertificateRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    @Autowired
    private CertificateService certificateService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClassCertificateRepository classCertificateRepository;
    @Autowired
    private CertificateInfoRepository certificateInfoRepository;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MemberEntity issuer = new MemberEntity();
        issuer.setUsername("issuer");
        issuer.setPassword("issuerpassword");
        issuer.setName("issuer");
        issuer.setEmail("issuer@issuer.com");
        issuer.setPhone("01001010101");

        MemberEntity user = new MemberEntity();
        user.setUsername("test");
        user.setPassword("testpassword");
        user.setName("test");
        user.setRole(Role.ROLE_USER);
        memberRepository.save(issuer);
        memberRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
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
                "file", // 파라미터 이름(file과 일치해야 함)
                "filename.txt", // 테스트 파일 이름
                "text/plain", // 콘텐츠 타입
                "This is the file content".getBytes()); // 파일의 내용
        classCertificateDTO.setFile(mockFile);
        // when
        assertDoesNotThrow(() -> certificateService.createClassCertificate(classCertificateDTO, authentication));
        // then
        assertEquals(1, classCertificateRepository.findAll().size());
        assertEquals(1, certificateInfoRepository.findAll().size());

        ClassCertificateEntity classCertificate = classCertificateRepository.findAll().get(0);
        CertificateInfoEntity certificateInfo = certificateInfoRepository.findAll().get(0);

        assertNotNull(classCertificate.getCertificateInfo());
        assertNotNull(certificateInfo.getClassCertificate());
        assertEquals(classCertificate.getCertificateInfo().getId(), certificateInfo.getId());
        assertEquals(certificateInfo.getClassCertificate().getId(), classCertificate.getId());
    }
}