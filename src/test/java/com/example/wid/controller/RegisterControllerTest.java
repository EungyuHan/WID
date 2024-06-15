package com.example.wid.controller;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("일반 사용자 회원가입 성공")
    void registerUser() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .username("user")
                .password("userpassword")
                .name("user")
                .email("user@user.com")
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        memberRepository.findByUsername("user").ifPresent(memberEntity -> {
            assertEquals("ROLE_USER", memberEntity.getRole().toString());
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .username("user")
                .password("userpassword")
                .name("user")
                .email("user@user.com")
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "user")
                .param("password", "userpassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공시 헤더에 JWT토큰 발행")
    void loginWithJWT() throws Exception {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .username("user")
                .password("userpassword")
                .name("user")
                .email("user@user.com")
                .phone("010-1234-5678")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/register/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "user")
                        .param("password", "userpassword"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String header = mvcResult.getResponse().getHeader(AUTHORIZATION);
        assertThat(header).isNotNull();
        assertThat(header).startsWith("Bearer ");
    }
}