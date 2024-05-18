package com.example.wid.repository;

import com.example.wid.entity.ClassCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassCertificateRepository extends JpaRepository<ClassCertificateEntity, Long> {
}
