package com.example.wid.service;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.controller.exception.AlreadyExistsMemberException;
import com.example.wid.entity.enums.Role;
import com.example.wid.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("User Register Test")
    void registerUser() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("test");
        registerDTO.setPassword("testpassowrd");
        registerDTO.setName("test");
        registerDTO.setEmail("test@test.com");
        registerDTO.setPhone("01001010101");
        // when
        boolean registered = memberService.registerMember(registerDTO, Role.ROLE_USER);
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
        memberService.registerMember(firstRegisterDTO, Role.ROLE_USER);
        // when
        RegisterDTO secondRegisterDTO = new RegisterDTO();
        secondRegisterDTO.setUsername("test");
        secondRegisterDTO.setPassword("testpassword");
        secondRegisterDTO.setName("test");
        secondRegisterDTO.setEmail("test@test.com");
        secondRegisterDTO.setPhone("01001010101");
        // then
        AlreadyExistsMemberException thrown = assertThrows(AlreadyExistsMemberException.class,
                () -> memberService.registerMember(secondRegisterDTO, Role.ROLE_USER));
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
                () -> memberService.registerMember(registerDTO, Role.ROLE_USER));
        assertEquals("모든 정보를 입력해주세요.", thrown.getMessage());
    }
}