package com.example.wid.service.strategy;

import com.example.wid.dto.base.BaseCertificateJson;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;

public interface CertificateStrategy {
    CertificateType getType();

    void save(BaseCertificateEntity entity, CertificateInfoEntity info);

    BaseCertificateEntity getCertificate(Long certificateId);

    BaseCertificateJson getCertificateJson(CertificateInfoEntity certificateInfo);
}
