package com.example.wid.repository;

import com.example.wid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByUsername(String username);

    Optional<MemberEntity> findByUsername(String username);
}