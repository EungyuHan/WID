package com.example.wid.repository;

import com.example.wid.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> findByEmail(String email);
    Boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);
    @Transactional
    @Modifying
    @Query("UPDATE MemberEntity m SET m.publicKey = :publicKey WHERE m.username = :username")
    void updatePublicKeyByUsername(String username, String publicKey);
}
