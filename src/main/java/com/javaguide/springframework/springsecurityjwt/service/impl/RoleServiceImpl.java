package com.javaguide.springframework.springsecurityjwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguide.springframework.springsecurityjwt.dto.request.RoleRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;
import com.javaguide.springframework.springsecurityjwt.dto.response.impl.RoleResponseImpl;
import com.javaguide.springframework.springsecurityjwt.entity.Permission;
import com.javaguide.springframework.springsecurityjwt.entity.Role;
import com.javaguide.springframework.springsecurityjwt.exception.AppException;
import com.javaguide.springframework.springsecurityjwt.exception.ErrorCode;
import com.javaguide.springframework.springsecurityjwt.repository.PermissionRepository;
import com.javaguide.springframework.springsecurityjwt.repository.RoleRepository;
import com.javaguide.springframework.springsecurityjwt.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
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

        // convert request to Role
        var role = objectMapper.convertValue(request, Role.class);

        Set<Permission> permissions = new HashSet<>();
        boolean permissionExists = true;

        // check if permission exists
        for (String permissionName : request.permissions()){
            Optional<Permission> permission = permissionRepository.findByName(permissionName);
            if (permission.isPresent()){
                permissions.add(permission.get());
            } else {
                permissionExists = false;
                break;
            }
        }

        // throw exception if permission not exists
        if(!permissionExists){
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTS);
        }

        // save role and return response
        role.setPermissions(permissions);
        roleRepository.save(role);
        return roleRepository.getRoleResponse(role.getName());
    }
}
