package com.javaguide.springframework.springsecurityjwt.controller.user;

import com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseDto;
import com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseTest;
import com.javaguide.springframework.springsecurityjwt.entity.User;
import com.javaguide.springframework.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity
                .ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    @PostAuthorize("hasRole('ADMIN') || returnObject.body.email == authentication.name")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        return ResponseEntity
                .ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(){
        return ResponseEntity
                .ok(userService.myInfo());
    }

}
