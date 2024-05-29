package com.example.wid.repository;

import com.example.wid.entity.CompetitionCertificateEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetitionCertificateRepository extends JpaRepository<CompetitionCertificateEntityEntity, Long> {
    Optional<CompetitionCertificateEntityEntity> findByCertificateInfo_Id(Long certificateInfoId);
}
