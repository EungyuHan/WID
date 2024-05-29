package com.example.wid.repository;

import com.example.wid.entity.ClassCertificateEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassCertificateRepository extends JpaRepository<ClassCertificateEntityEntity, Long> {
    Optional<ClassCertificateEntityEntity> findByCertificateInfo_Id(Long certificateInfoId);
}
