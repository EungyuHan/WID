package com.example.wid.service;

import com.example.wid.WidApplication;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.FolderEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.repository.FolderCertificateRepository;
import com.example.wid.repository.FolderRepository;
import com.example.wid.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@Transactional
class FolderServiceTest {
    @Autowired
    private FolderService folderService;
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private FolderCertificateRepository folderCertificateRepository;
    @Autowired
    private CertificateInfoRepository certificateInfoRepository;
    @Autowired
    private MemberRepository memberRepository;

    private MemberEntity user;

    @BeforeEach
    void setUp() {
        user = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .email("user@user.com")
                .name("user")
                .phone("01022222222")
                .build();
        memberRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        folderCertificateRepository.deleteAll();
        folderRepository.deleteAll();
        certificateInfoRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void createFolder() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        folderService.createFolder("folder", authentication);

        assertEquals(1, folderRepository.findAll().size());
        FolderEntity savedFolder = folderRepository.findAll().get(0);
        assertEquals("folder", savedFolder.getFolderName());
    }

    @Test
    void insertCertificates() {
        FolderEntity folder = FolderEntity.builder()
                .folderName("folder")
                .user(user)
                .build();
        folderRepository.save(folder);

        CertificateInfoEntity certificate1 = CertificateInfoEntity.builder()
                .user(user)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .build();
        CertificateInfoEntity saved1 = certificateInfoRepository.save(certificate1);

        CertificateInfoEntity certificate2 = CertificateInfoEntity.builder()
                .user(user)
                .certificateType(CertificateType.COMPETITION_CERTIFICATE)
                .build();
        CertificateInfoEntity saved2 = certificateInfoRepository.save(certificate2);


        List<Long> certificateIds = List.of(saved1.getId(), saved2.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        folderService.insertCertificates(folder.getId(), certificateIds, authentication);

        assertEquals(2, folderCertificateRepository.findAll().size());

        folderCertificateRepository.findAll().forEach(folderCertificate -> {
            assertEquals(folder.getId(), folderCertificate.getFolder().getId());
            assertTrue(certificateIds.contains(folderCertificate.getCertificate().getId()));
        });
    }
}