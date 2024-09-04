package com.example.wid.repository;

import com.example.wid.entity.BelongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelongRepository extends JpaRepository<BelongEntity, Long> {
}
