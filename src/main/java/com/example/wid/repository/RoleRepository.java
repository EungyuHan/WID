package com.example.wid.repository;

import com.example.wid.entity.Role;
import com.example.wid.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(Role role);
}
