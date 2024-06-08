package com.example.wid.repository;

import com.example.wid.entity.SentCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SentCertificateRepository extends JpaRepository<SentCertificateEntity, Long> {
//    Optional<SentCertificateEntity> findByVerifierID(Long ID);
//
//    List<SentCertificateEntity> findAllByVerifier(Long ID);
}
