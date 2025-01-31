package com.testing.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.test.request.UserRequest;
import com.testing.test.response.UserResponse;
import com.testing.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;


@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private static final String END_POINT_PATH = "/users";

    @Test
    public void testAddUserAPI() throws Exception{
        UserRequest user = new UserRequest();
        user.setUsername("user2");
        user.setPassword("{noop}1234");

        String userJson = objectMapper.writeValueAsString(user);
        UserResponse response = new UserResponse();
        response.setUsername("user2");
        response.setId(2L);

        when(userService.add(user)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(response));
        mockMvc.perform(post(END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user2"))
                .andExpect(jsonPath("$.id").value(2));
    }
}
