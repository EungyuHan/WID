package com.example.wid.controller;

import com.example.wid.service.RsaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    @MockBean
    private RsaService rsaService;

    @Test
    @WithMockUser(username = "test")
    @DisplayName("RSA 키 페어 생성 성공")
    void generateRsaKeyPair() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rsa/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
