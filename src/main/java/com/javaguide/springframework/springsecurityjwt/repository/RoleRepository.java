package com.javaguide.springframework.springsecurityjwt.repository;

import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    @Query(value = """
        select r.role_name  as roleName, string_agg(p.permission_name , ',') as permissions
        from roles r
        join roles_permissions rp on rp.role_id  = r.id
        join permissions p on p.id = rp.permission_id\s
        where r.role_name  = :roleName
        group by r.role_name
    """, nativeQuery = true)
    RoleResponse getRoleResponse(@Param("roleName") String roleName);
}
