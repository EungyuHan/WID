package com.example.wid.repository;

import com.example.wid.entity.CertificateInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateInfoRepository extends JpaRepository<CertificateInfoEntity, Long> {
}
