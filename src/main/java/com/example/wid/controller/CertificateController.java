package com.example.wid.controller;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.dto.CompetitionCertificateDTO;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/user/class")
    public ResponseEntity<String> createClassCertificate(@ModelAttribute ClassCertificateDTO classCertificateDTO) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.createCertificate(classCertificateDTO, authentication, CertificateType.CLASS_CERTIFICATE);

        return ResponseEntity.ok("수업 증명서 생성 완료");
    }

    @PostMapping("/user/competition")
    public ResponseEntity<String> createCompetitionCertificate(@ModelAttribute CompetitionCertificateDTO competitionCertificateDTO) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.createCertificate(competitionCertificateDTO, authentication, CertificateType.COMPETITION_CERTIFICATE);

        return ResponseEntity.ok("대회 증명서 생성 완료");
    }

    @PostMapping("/issuer/sign")
    public ResponseEntity<String> signCertificateIssuer(@RequestBody Long certificateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.signCertificateIssuer(certificateId, authentication);

        return ResponseEntity.ok("증명서 1차 서명 완료");
    }

    @PostMapping("/user/sign")
    public ResponseEntity<String> signCertificateUser(@RequestBody Long certificateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.signCertificateUser(certificateId, authentication);

        return ResponseEntity.ok("증명서 2차 서명 완료");
    }
}
