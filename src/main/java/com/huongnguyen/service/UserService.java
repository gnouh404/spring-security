package com.huongnguyen.service;

import com.huongnguyen.dto.request.UserRequest;
import com.huongnguyen.dto.request.UserUpdateRequest;
import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto myInfo();

    User getUserById(Integer id);

    void updateUser(UserUpdateRequest request);

    void createUser(UserRequest request);
}
