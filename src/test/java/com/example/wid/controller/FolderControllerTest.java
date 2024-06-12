package com.example.wid.controller;

import com.example.wid.configuration.JwtUserRequestPostProcessor;
import com.example.wid.dto.FolderCertificatesDTO;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.FolderEntity;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.entity.enums.Role;
import com.example.wid.repository.CertificateInfoRepository;
import com.example.wid.repository.FolderRepository;
import com.example.wid.repository.MemberRepository;
import com.example.wid.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FolderControllerTest {
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
    private FolderRepository folderRepository;

    @Test
    @DisplayName("폴더 생성 성공")
    void createFolder() throws Exception{
        MemberEntity user = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/folder/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("folderName"))
                        .with(JwtUserRequestPostProcessor.jwtUser(jwtUtil, "user", user.getRole())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("폴더에 인증서 추가 성공")
    void insertCertificateInFoler() throws Exception {
        MemberEntity user = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .role(Role.ROLE_USER)
                .build();
        MemberEntity issuer = MemberEntity.builder()
                .username("issuer")
                .password("issuerpassword")
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(user);
        memberRepository.save(issuer);

        FolderEntity folder = FolderEntity.builder()
                .folderName("folderName")
                .user(user)
                .build();
        FolderEntity savedFolder = folderRepository.save(folder);

        CertificateInfoEntity firstCertificate = CertificateInfoEntity.builder()
                .user(user)
                .issuer(issuer)
                .certificateType(CertificateType.CLASS_CERTIFICATE)
                .build();
        CertificateInfoEntity savedCertificate = certificateInfoRepository.save(firstCertificate);

        FolderCertificatesDTO folderCertificatesDTO = FolderCertificatesDTO.builder()
                .folderId(savedFolder.getId())
                .certificateIds(List.of(new Long[]{savedCertificate.getId()}))
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/folder/insert/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(folderCertificatesDTO))
                        .with(JwtUserRequestPostProcessor.jwtUser(jwtUtil, "user", user.getRole())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}