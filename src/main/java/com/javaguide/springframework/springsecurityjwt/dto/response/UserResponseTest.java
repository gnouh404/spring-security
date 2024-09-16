package com.javaguide.springframework.springsecurityjwt.dto.response;

import java.util.Set;


public record UserResponseTest(String firstName, String lastName, String email, Set<String> roles) {


}
