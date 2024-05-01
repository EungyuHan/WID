package com.example.wid.controller;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final MemberService memberService;

    @Autowired
    public RegisterController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 일반 사용자 회원가입
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@ModelAttribute RegisterDTO registerDTO) {
        boolean isRegister = memberService.registerUser(registerDTO);

        // 회원가입 성공시 200 OK, 실패시 400 Bad Request
        if(isRegister){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
