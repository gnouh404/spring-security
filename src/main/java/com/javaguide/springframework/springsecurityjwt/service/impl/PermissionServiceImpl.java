package com.javaguide.springframework.springsecurityjwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguide.springframework.springsecurityjwt.dto.request.PermissionRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.PermissionResponse;
import com.javaguide.springframework.springsecurityjwt.entity.Permission;
import com.javaguide.springframework.springsecurityjwt.exception.AppException;
import com.javaguide.springframework.springsecurityjwt.exception.ErrorCode;
import com.javaguide.springframework.springsecurityjwt.repository.PermissionRepository;
import com.javaguide.springframework.springsecurityjwt.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("permissionServiceImpl")
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final ObjectMapper objectMapper;

    public PermissionServiceImpl(PermissionRepository permissionRepository, ObjectMapper objectMapper) {
        this.permissionRepository = permissionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = objectMapper.convertValue(request, Permission.class);

        if (permissionRepository.findByName(request.name()).isPresent()){
            throw new AppException(ErrorCode.PERMISSION_EXISTS);
        } else {
            permission.setName(request.name());
            permissionRepository.save(permission);
            return permissionRepository.getPermissionResponse(request.name());
        }


    }
}
