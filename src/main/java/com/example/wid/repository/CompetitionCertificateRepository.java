package com.example.wid.repository;

import com.example.wid.entity.CompetitionCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionCertificateRepository extends JpaRepository<CompetitionCertificateEntity, Long> {
    Optional<CompetitionCertificateEntity> findByCertificateInfo_Id(Long certificateInfoId);
}
