package com.javaguide.springframework.springsecurityjwt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseDto;
import com.javaguide.springframework.springsecurityjwt.entity.User;
import com.javaguide.springframework.springsecurityjwt.exception.AppException;
import com.javaguide.springframework.springsecurityjwt.exception.ErrorCode;
import com.javaguide.springframework.springsecurityjwt.repository.UserRepository;
import com.javaguide.springframework.springsecurityjwt.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAllUsers();
    }

    public User getUserById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponseDto myInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return objectMapper.convertValue(user, UserResponseDto.class);
    }
}
