package com.javaguide.springframework.springsecurityjwt.controller.authorization;

import com.javaguide.springframework.springsecurityjwt.dto.request.PermissionRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.PermissionResponse;
import com.javaguide.springframework.springsecurityjwt.service.PermissionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(@Qualifier("permissionServiceImpl")PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/createPermission")
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionService.create(request));
    }
}
