package com.example.wid.controller;

import com.example.wid.dto.RegisterDTO;
import com.example.wid.entity.enums.Role;
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
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        boolean isRegister = memberService.registerMember(registerDTO, Role.ROLE_USER);

        // 회원가입 성공시 200 OK, 실패시 400 Bad Request
        if(isRegister){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/issuer")
    public ResponseEntity<?> registerIssuer(@RequestBody RegisterDTO registerDTO) {
        boolean isRegister = memberService.registerMember(registerDTO, Role.ROLE_ISSUER);

        // 회원가입 성공시 200 OK, 실패시 400 Bad Request
        if(isRegister){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verifier")
    public ResponseEntity<?> registerVerifier(@RequestBody RegisterDTO registerDTO) {
        boolean isRegister = memberService.registerMember(registerDTO, Role.ROLE_VERIFIER);

        // 회원가입 성공시 200 OK, 실패시 400 Bad Request
        if(isRegister){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
