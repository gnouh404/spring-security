package com.huongnguyen.repository;

import com.huongnguyen.dto.response.PermissionResponse;
import com.huongnguyen.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByName(String name);

    @Query("""
     select new com.huongnguyen.dto.response.PermissionResponse(p.name)
     from Permission p
     where p.name = :name
    """
    )
    PermissionResponse getPermissionResponse(String name);
}
