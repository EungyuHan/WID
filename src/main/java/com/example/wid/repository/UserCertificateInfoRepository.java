package com.example.wid.repository;

import com.example.wid.entity.UserCertificateInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCertificateInfoRepository extends JpaRepository<UserCertificateInfoEntity, Long> {
}
