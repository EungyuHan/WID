package com.example.wid.repository;

import com.example.wid.entity.SentCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentCertificateRepository extends JpaRepository<SentCertificateEntity, Long> {
}
