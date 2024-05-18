package com.example.wid.controller;

import com.example.wid.dto.ClassCertificateDTO;
import com.example.wid.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.ParseException;

@Controller
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/class")
    public void createClassCertificate(ClassCertificateDTO classCertificateDTO) throws IOException, ParseException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        certificateService.createClassCertificate(classCertificateDTO, authentication);
    }
}
