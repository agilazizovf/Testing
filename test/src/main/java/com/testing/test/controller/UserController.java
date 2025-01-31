package com.testing.test.controller;

import com.testing.test.request.UserRequest;
import com.testing.test.response.UserResponse;
import com.testing.test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> add(@RequestBody @Valid UserRequest userRequest) {
        return userService.add(userRequest);
    }
}
