package com.example.wid.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "folder_document")
@Getter
@Setter
@NoArgsConstructor
public class FolderCertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    FolderEntity folder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    CertificateInfoEntity certificate;

    @Builder
    public FolderCertificateEntity(FolderEntity folder, CertificateInfoEntity certificate) {
        this.folder = folder;
        this.certificate = certificate;
    }
}
