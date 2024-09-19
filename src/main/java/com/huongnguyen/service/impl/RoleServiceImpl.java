package com.huongnguyen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huongnguyen.dto.request.RoleRequest;
import com.huongnguyen.dto.response.RoleResponse;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.entity.Permission;
import com.huongnguyen.entity.Role;
import com.huongnguyen.repository.PermissionRepository;
import com.huongnguyen.repository.RoleRepository;
import com.huongnguyen.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

    public Role create(RoleRequest request) {

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
        } else {
            role.setPermissions(permissions);
            return roleRepository.save(role);
        }

        // save role and return response
    }

    public List<RoleResponse> getAllRoleWithPermissions() {
        return roleRepository.findAllRoleWithPermissions();
    }
}
