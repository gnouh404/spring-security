package com.javaguide.springframework.springsecurityjwt.controller.authorization;

import com.javaguide.springframework.springsecurityjwt.dto.request.RoleRequest;
import com.javaguide.springframework.springsecurityjwt.dto.response.RoleResponse;
import com.javaguide.springframework.springsecurityjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(@Qualifier("roleService") RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/createRole")
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.create(request));
    }
}
