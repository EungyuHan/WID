package com.example.wid.controller;

import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.dto.FolderCertificatesDTO;
import com.example.wid.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createFolder(@RequestBody Map<String, String> map) {
        String folderName = map.get("folderName");
        System.out.println("folderName : " + folderName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long folderId = folderService.createFolder(folderName, authentication);
        return ResponseEntity.ok(folderId);
    }

    @PostMapping("/insert/certificates")
    public ResponseEntity<String> insertCertificates(@RequestBody FolderCertificatesDTO folderCertificatesDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        folderService.insertCertificates(folderCertificatesDTO, authentication);
        return ResponseEntity.ok("증명서 삽입완료");
    }

    @GetMapping("/get/certificates")
    public ResponseEntity<List<CertificateInfoEntity>> getCertificatesInFolder(@RequestBody Map<String, Long> map) {
        Long folderId = map.get("folderId");
        Authentication authenticatoin = SecurityContextHolder.getContext().getAuthentication();
        List<CertificateInfoEntity> certificates = folderService.getCertificatesInFolder(folderId, authenticatoin);
        return ResponseEntity.ok(certificates);
    }

    @PostMapping("/{folderId}/send")
    public ResponseEntity<String> sendCertificatesToVerifier(@PathVariable Long folderId, @RequestBody Map<String, String> map) {
        String verifierEmail = map.get("verifierEmail");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        folderService.sendCertificatesToVerifier(folderId, verifierEmail, authentication);
        return ResponseEntity.ok("검증자에게 전송 완료");
    }
}
