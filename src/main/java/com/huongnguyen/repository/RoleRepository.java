package com.huongnguyen.repository;

import com.huongnguyen.dto.response.RoleResponse;
import com.huongnguyen.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {


    @Query("""
        select r.name as name,
               string_agg(p.name, ',') as permissions
        from Role r
        join RolesPermissions rp on rp.id.roleId = r.id
        join Permission p on p.id = rp.id.permissionId
        where r.name = :roleName
        group by r.name
    """)
    RoleResponse getRoleResponse(@Param("roleName") String roleName);

    @Query("""
    select 
        r.name as name, 
        string_agg(p.name, ',') as permissions
    from Role r
    join RolesPermissions rp on rp.id.roleId = r.id
    join Permission p on p.id = rp.id.permissionId
    group by r.name
    """)
    List<RoleResponse> findAllRoleWithPermissions();


    Optional<Role> findByName(String name);
}
