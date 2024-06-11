package com.example.wid.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FolderCertificatesDTO {
    private Long folderId;
    private List<Long> certificateIds;

    @Builder
    public FolderCertificatesDTO(Long folderId, List<Long> certificateIds) {
        this.folderId = folderId;
        this.certificateIds = certificateIds;
    }
}
