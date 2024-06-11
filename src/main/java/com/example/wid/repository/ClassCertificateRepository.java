package com.example.wid.repository;

import com.example.wid.entity.ClassCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassCertificateRepository extends JpaRepository<ClassCertificateEntity, Long> {
    Optional<ClassCertificateEntity> findByCertificateInfo_Id(Long certificateInfoId);
}
