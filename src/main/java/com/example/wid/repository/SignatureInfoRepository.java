package com.example.wid.repository;

import com.example.wid.entity.SignatureInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureInfoRepository extends JpaRepository<SignatureInfoEntity, Long> {
}
