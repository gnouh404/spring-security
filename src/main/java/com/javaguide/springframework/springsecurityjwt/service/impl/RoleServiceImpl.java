package com.javaguide.springframework.springsecurityjwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguide.springframework.springsecurityjwt.dto.request.RoleRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Permission;
import com.javaguide.springframework.springsecurityjwt.entity.Role;
import com.javaguide.springframework.springsecurityjwt.repository.PermissionRepository;
import com.javaguide.springframework.springsecurityjwt.repository.RoleRepository;
import com.javaguide.springframework.springsecurityjwt.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final ObjectMapper objectMapper;


    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, ObjectMapper objectMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.objectMapper = objectMapper;
    }

    public RoleResponse create(RoleRequest request) {
        var role = objectMapper.convertValue(request, Role.class);

        Set<Permission> permissions = new HashSet<>();
        for (String permissionName : request.permissions()){
            permissionRepository.findByName(permissionName).ifPresent(permissions::add);
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleRepository.getRoleResponse(role.getName());
    }
}
