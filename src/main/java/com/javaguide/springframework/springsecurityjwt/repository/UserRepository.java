package com.javaguide.springframework.springsecurityjwt.repository;

import com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseDto;
import com.javaguide.springframework.springsecurityjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT new com.javaguide.springframework.springsecurityjwt.dto.response.UserResponseDto(u.firstName, u.lastName, u.email) FROM User u")
    List<UserResponseDto> findAllUsers();


    Optional<User> findById(Integer id);
}
