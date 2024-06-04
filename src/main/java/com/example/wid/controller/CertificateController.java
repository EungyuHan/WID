package com.example.wid.controller;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public void createClassCertificate(ClassCertificateDTO classCertificateDTO) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.createCertificate(classCertificateDTO, authentication, CertificateType.CLASS_CERTIFICATE);
    }

    @PostMapping("/user/competition")
    public void createCompetitionCertificate(ClassCertificateDTO classCertificateDTO) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.createCertificate(classCertificateDTO, authentication, CertificateType.COMPETITION_CERTIFICATE);
    }

    @PostMapping("/issuer/sign")
    public void signCertificateIssuer(@RequestParam Long certificateId,@RequestParam String privateKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.signCertificateIssuer(certificateId, privateKey, authentication);
    }

    @PostMapping("/user/sign")
    public void signCertificateUser(@RequestParam Long certificateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.signCertificateUser(certificateId, authentication);
    }
}