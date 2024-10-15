package com.huongnguyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huongnguyen.dto.request.AuthenticationResquest;
import com.huongnguyen.dto.request.UserRequest;
import com.huongnguyen.dto.response.ApiResponse;
import com.huongnguyen.dto.response.LoginResponse;
import com.huongnguyen.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequest request;
    private ApiResponse response;



    @BeforeEach
    public void initData(){
        request = new UserRequest("John", "Cena", "jcena@gmail.com", "cena123");
        response = new ApiResponse(200, "Create user success");

    }

    // test create user is success
    @Test
    void createUserIsSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthenticationResquest loginRequest = new AuthenticationResquest("admin@gmail.com", "huong123");


        // get access token
        MvcResult result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(json, LoginResponse.class);
        String token = loginResponse.token();

        // given

        String content = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(201)
        );
    }
}
