package com.example.wid.repository;

import com.example.wid.entity.EncryptInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncryptInfoRepository extends JpaRepository<EncryptInfoEntity, Long> {
    Optional<EncryptInfoEntity> findByCertificateInfoId(Long certificateInfoId);
}
