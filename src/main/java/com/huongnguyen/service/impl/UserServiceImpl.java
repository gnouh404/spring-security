package com.huongnguyen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huongnguyen.dto.request.UserUpdateRequest;
import com.huongnguyen.entity.Role;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;
import com.huongnguyen.repository.RoleRepository;
import com.huongnguyen.repository.UserRepository;
import com.huongnguyen.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAllUsers();
    }


    public User getUserById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User updateUser(UserUpdateRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Set<Role> roles = new HashSet<>();
        request.roles().forEach(
                role -> {
                    Role roleEntity = roleRepository.findByName(role).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
                    roles.add(roleEntity);
                }
        );
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserResponseDto myInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return objectMapper.convertValue(user, UserResponseDto.class);
    }


}
