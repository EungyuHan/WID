package com.example.wid.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("home");
    }
    @GetMapping("/admin")
    public ResponseEntity<?> admin(){
        return ResponseEntity.ok("admin");
    }

    @GetMapping("/user")
    public ResponseEntity<?> user(){
        return ResponseEntity.ok("user");
    }

    @GetMapping("/issuer")
    public ResponseEntity<?> issuer(){
        return ResponseEntity.ok("issuer");
    }

    @GetMapping("/verifier")
    public ResponseEntity<?> verifier(){
        return ResponseEntity.ok("verifier");
    }
}
