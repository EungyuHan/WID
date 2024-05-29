package com.example.wid.dto.base;

import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;
import org.springframework.web.multipart.MultipartFile;

public interface BaseCertificateDTO {
    String getIssuerEmail();
    MultipartFile getFile();
    BaseCertificateEntity toCertificateEntity(CertificateType certificateType);
}
