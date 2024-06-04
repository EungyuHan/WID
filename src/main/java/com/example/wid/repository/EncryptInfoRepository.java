package com.example.wid.repository;

import com.example.wid.entity.EncryptInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncryptInfoRepository extends JpaRepository<EncryptInfoEntity, Long> {
}
