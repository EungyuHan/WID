package com.example.wid.controller;

import com.example.wid.configuration.JwtUserRequestPostProcessor;
import com.example.wid.entity.MemberEntity;
import com.example.wid.entity.enums.Role;
import com.example.wid.repository.MemberRepository;
import com.example.wid.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RsaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MemberRepository memberRepository;

    MemberEntity user;

    @BeforeEach
    void setUp() {
        user = MemberEntity.builder()
                .username("user")
                .password("userpassword")
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(user);
    }

    @Test
    @DisplayName("RSA 키 페어 생성 성공")
    void generateRsaKeyPair() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rsa/generate")
                        .with(JwtUserRequestPostProcessor.jwtUser(jwtUtil, user.getUsername(), user.getRole()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
