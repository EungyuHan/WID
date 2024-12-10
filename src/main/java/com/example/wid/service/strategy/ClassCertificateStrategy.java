package com.example.wid.service.strategy;

import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.dto.ClassCertificateJson;
import com.example.wid.dto.base.BaseCertificateJson;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.ClassCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassCertificateStrategy implements CertificateStrategy {
    private final ClassCertificateRepository repository;

    @Autowired
    public ClassCertificateStrategy(ClassCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CertificateType getType() {
        return CertificateType.CLASS_CERTIFICATE;
    }

    @Override
    public void save(BaseCertificateEntity entity, CertificateInfoEntity info) {
        try {
            ClassCertificateEntity classEntity = (ClassCertificateEntity) entity;
            classEntity.setCertificateInfo(info);
            repository.save(classEntity);
        } catch (Exception e) {
            throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");
        }
    }

    @Override
    public BaseCertificateEntity getCertificate(Long certificateId) {
        BaseCertificateEntity baseCertificateEntity = null;
        if (repository.findByCertificateInfo_Id(certificateId).isPresent()) {
            baseCertificateEntity = repository.findByCertificateInfo_Id(certificateId)
                    .orElseThrow(() -> new InvalidCertificateException("인증서 정보가 올바르지 않습니다."));
        }
        return baseCertificateEntity;
    }

    @Override
    public BaseCertificateJson getCertificateJson(CertificateInfoEntity certificateInfo) {
        ClassCertificateEntity classCertificateEntity = (ClassCertificateEntity) getCertificate(
                certificateInfo.getId());
        if (classCertificateEntity == null) {
            throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");
        }
        return ClassCertificateJson.builder()
                .id(certificateInfo.getId())
                .storedFilename(classCertificateEntity.getStoredFilename())
                .name(classCertificateEntity.getName())
                .studentId(classCertificateEntity.getStudentId())
                .subject(classCertificateEntity.getSubject())
                .professor(classCertificateEntity.getProfessor())
                .summary(classCertificateEntity.getSummary())
                .term(classCertificateEntity.getTerm())
                .build();
    }
}