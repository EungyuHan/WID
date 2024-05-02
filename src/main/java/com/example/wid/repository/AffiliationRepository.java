package com.example.wid.repository;

import com.example.wid.entity.AffiliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationEntity, Long> {
}
