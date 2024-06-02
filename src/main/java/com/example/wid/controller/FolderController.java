package com.example.wid.controller;

import com.example.wid.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/folder")
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/create")
    public void createFolder(@RequestParam String folderName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        folderService.createFolder(folderName, authentication);
    }

    @PostMapping("/insert/certificates")
    public void insertCertificates(@RequestParam Long folderId, @RequestParam List<Long> certificateIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        folderService.insertCertificates(folderId, certificateIds, authentication);
    }
}
