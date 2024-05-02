package com.example.wid.service;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.entity.MemberEntity;
import com.example.wid.exception.AlreadyExistsMemberException;
import com.example.wid.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    void registerUser() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("test");
        registerDTO.setPassword("testpassowrd");
        registerDTO.setName("test");
        registerDTO.setEmail("test@test.com");
        registerDTO.setPhone("01001010101");
        // when
        boolean registered = memberService.registerUser(registerDTO);
        // then
        assertThat(registered).isTrue();
    }

    @Test
    void registerDuplicatedUser(){
        // given
        RegisterDTO firstRegisterDTO = new RegisterDTO();
        firstRegisterDTO.setUsername("test");
        firstRegisterDTO.setPassword("testpassword");
        firstRegisterDTO.setName("test");
        firstRegisterDTO.setEmail("test@test.com");
        firstRegisterDTO.setPhone("01001010101");
        memberService.registerUser(firstRegisterDTO);
        // when
        RegisterDTO secondRegisterDTO = new RegisterDTO();
        secondRegisterDTO.setUsername("test");
        secondRegisterDTO.setPassword("testpassword");
        secondRegisterDTO.setName("test");
        secondRegisterDTO.setEmail("test@test.com");
        secondRegisterDTO.setPhone("01001010101");
        // then
        AlreadyExistsMemberException thrown = assertThrows(AlreadyExistsMemberException.class,
                () -> memberService.registerUser(secondRegisterDTO));
        assertEquals("Member already exists", thrown.getMessage());
    }

    @Test
    void registerEmptyUser(){
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("");
        registerDTO.setPassword("");
        registerDTO.setName("");
        registerDTO.setEmail("");
        registerDTO.setPhone("");
        // when
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> memberService.registerUser(registerDTO));
        assertEquals("모든 정보를 입력해주세요.", thrown.getMessage());
    }
}