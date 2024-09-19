package com.huongnguyen.controller.user;

import com.huongnguyen.dto.request.UserUpdateRequest;
import com.huongnguyen.dto.response.ApiResponse;
import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;
import com.huongnguyen.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request){
        userService.updateUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(200,"Update success"));
    }

}
