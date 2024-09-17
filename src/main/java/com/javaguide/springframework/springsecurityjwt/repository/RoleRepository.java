package com.javaguide.springframework.springsecurityjwt.repository;

import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    @Query(value = """
        select r.role_name as name,
        string_agg(p.permission_name, ', ') as descriptions
        from roles r
        JOIN roles_permissions rp ON r.id = rp.role_id
        JOIN permissions p ON p.id = rp.permission_id
        where r.role_name = :roleName
        group by r.role_name
    """, nativeQuery = true)
    RoleResponse getRoleResponse(@Param("roleName") String roleName);
}
