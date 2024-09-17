package com.javaguide.springframework.springsecurityjwt.repository;

import com.javaguide.springframework.springsecurityjwt.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String name);
}
