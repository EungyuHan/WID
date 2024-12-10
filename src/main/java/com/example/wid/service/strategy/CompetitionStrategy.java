package com.example.wid.service.strategy;

import com.example.wid.controller.exception.InvalidCertificateException;
import com.example.wid.dto.CompetitionCertificateJson;
import com.example.wid.dto.base.BaseCertificateJson;
import com.example.wid.entity.CertificateInfoEntity;
import com.example.wid.entity.ClassCertificateEntity;
import com.example.wid.entity.CompetitionCertificateEntity;
import com.example.wid.entity.base.BaseCertificateEntity;
import com.example.wid.entity.enums.CertificateType;
import com.example.wid.repository.CompetitionCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompetitionStrategy implements CertificateStrategy {
    private final CompetitionCertificateRepository repository;

    @Autowired
    public CompetitionStrategy(CompetitionCertificateRepository repository) {
        this.repository = repository;
    }

    @Override
    public CertificateType getType() {
        return CertificateType.COMPETITION_CERTIFICATE;
    }

    @Override
    public void save(BaseCertificateEntity entity, CertificateInfoEntity info) {
        try {
            CompetitionCertificateEntity competitionCertificate = (CompetitionCertificateEntity) entity;
            competitionCertificate.setCertificateInfo(info);
            repository.save(competitionCertificate);
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
        CompetitionCertificateEntity competitionCertificateEntity = (CompetitionCertificateEntity) getCertificate(
                certificateInfo.getId());
        if (competitionCertificateEntity == null) {
            throw new InvalidCertificateException("인증서 정보가 올바르지 않습니다.");
        }
        return CompetitionCertificateJson.builder()
                .id(certificateInfo.getId())
                .storedFilename(competitionCertificateEntity.getStoredFilename())
                .competitionName(competitionCertificateEntity.getCompetitionName())
                .achievement(competitionCertificateEntity.getAchievement())
                .organizer(competitionCertificateEntity.getOrganizer())
                .summary(competitionCertificateEntity.getSummary())
                .term(competitionCertificateEntity.getTerm())
                .build();
    }
}