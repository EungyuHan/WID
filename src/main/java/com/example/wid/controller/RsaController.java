package com.example.wid.controller;

import com.example.wid.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/rsa")
public class RsaController {
    private final RsaService rsaService;
    @Autowired
    public RsaController(RsaService rsaService) {
        this.rsaService = rsaService;
    }

    @GetMapping("/generate")
    public ResponseEntity generateRsaKeyPair() throws NoSuchAlgorithmException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        rsaService.generateRsaKeyPair(authentication);
        return ResponseEntity.ok().build();
    }

}
