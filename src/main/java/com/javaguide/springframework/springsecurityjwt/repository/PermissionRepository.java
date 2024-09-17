package com.javaguide.springframework.springsecurityjwt.repository;

import com.javaguide.springframework.springsecurityjwt.dto.response.PermissionResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String name);

    @Query("""
     select new com.javaguide.springframework.springsecurityjwt.dto.response.PermissionResponse(p.name)
     from Permission p
     where p.name = :name
    """
    )
    PermissionResponse getPermissionResponse(String name);
}
