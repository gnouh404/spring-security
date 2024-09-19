package com.huongnguyen.service;

import com.huongnguyen.dto.request.UserUpdateRequest;
import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;

import java.util.List;

public interface UserService {

    public List<UserResponseDto> getAllUsers();

    public UserResponseDto myInfo();

    public User getUserById(Integer id);

    public User updateUser(UserUpdateRequest request);
}
