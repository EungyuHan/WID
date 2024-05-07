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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        Optional<String> privateKey = rsaService.generateRsaKeyPair(authentication);
        if(privateKey.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else{
            Map<String, String> keyPair = new HashMap<>();
            keyPair.put("privateKey", privateKey.get());
            return ResponseEntity.ok(keyPair);
        }
    }

}
