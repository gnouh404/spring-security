package com.huongnguyen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huongnguyen.dto.request.ProfileCreationRequest;
import com.huongnguyen.dto.request.UserRequest;
import com.huongnguyen.dto.request.UserUpdateRequest;
import com.huongnguyen.entity.Role;
import com.huongnguyen.exception.AppException;
import com.huongnguyen.exception.ErrorCode;
import com.huongnguyen.dto.response.UserResponseDto;
import com.huongnguyen.entity.User;
import com.huongnguyen.repository.RoleRepository;
import com.huongnguyen.repository.UserRepository;
import com.huongnguyen.repository.client.ProfileClient;
import com.huongnguyen.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userServiceImpl")
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProfileClient profileClient;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ProfileClient profileClient) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.profileClient = profileClient;
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAllUsers();
    }


    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void updateUser(UserUpdateRequest request) {
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

        userRepository.save(user);
    }

    @Override
    public void createUser(UserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        // set information to user
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setActive(false);

        // set role to user
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByName("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));
        user.setRoles(role);

        // save user to db
        userRepository.save(user);

        ProfileCreationRequest profileRequest = objectMapper.convertValue(request,ProfileCreationRequest.class);
        profileRequest.setUserId(user.getId());
        profileClient.createProfile(profileRequest);
    }

    public UserResponseDto myInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return objectMapper.convertValue(user, UserResponseDto.class);
    }


}
