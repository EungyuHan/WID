package com.example.wid.repository;

import com.example.wid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);

    Boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);
}
