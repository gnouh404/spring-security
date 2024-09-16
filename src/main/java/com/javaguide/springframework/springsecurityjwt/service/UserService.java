package com.javaguide.springframework.springsecurityjwt.service;

import com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseDto;
import com.javaguide.springframework.springsecurityjwt.entity.User;

import java.util.List;

public interface UserService {

    public List<UserResponseDto> getAllUsers();

    public UserResponseDto myInfo();

    public User getUserById(Integer id);
}
