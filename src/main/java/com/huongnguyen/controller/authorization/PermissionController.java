package com.huongnguyen.controller.authorization;

import com.huongnguyen.dto.request.PermissionRequest;
import com.huongnguyen.dto.response.ApiResponse;
import com.huongnguyen.service.PermissionService;
import com.huongnguyen.dto.response.PermissionResponse;
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
    public ResponseEntity<ApiResponse> create(@RequestBody PermissionRequest request){
        permissionService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(200,"Create permission success"));
    }
}
