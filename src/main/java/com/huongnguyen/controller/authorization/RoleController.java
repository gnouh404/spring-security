package com.huongnguyen.controller.authorization;

import com.huongnguyen.dto.request.AddPermissionToRole;
import com.huongnguyen.dto.request.RoleRequest;
import com.huongnguyen.dto.response.ApiResponse;
import com.huongnguyen.dto.response.RoleResponse;
import com.huongnguyen.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(@Qualifier("roleService") RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/createRole")
    public ResponseEntity create(@RequestBody RoleRequest request){
        roleService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(200, "Create role successfully"));
    }

    @PreAuthorize("hasAuthority('VIEW_DATA')")
    @GetMapping("/getAllRoles")
    public ResponseEntity<List<RoleResponse>> getAllRoles(){
        return ResponseEntity
                .ok(roleService.getAllRoleWithPermissions());
    }

    @PostMapping("/addPermissionToRole")
    public ResponseEntity<ApiResponse> addPermissionToRole(@RequestBody AddPermissionToRole request){
        roleService.addPermissionToRole(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(200, "Add permission to role successfully"));
    }
}
