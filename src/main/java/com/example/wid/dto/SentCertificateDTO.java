package com.example.wid.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SentCertificateDTO {
    private Long sentCertificateId;
    private Long folderId;
    private String userEmail;
    private String folderName;

    @Builder
    public SentCertificateDTO(Long sentCertificateId, Long folderId, String userEmail, String folderName) {
        this.sentCertificateId = sentCertificateId;
        this.folderId = folderId;
        this.userEmail = userEmail;
        this.folderName = folderName;
    }
}
