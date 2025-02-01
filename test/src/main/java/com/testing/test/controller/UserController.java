package com.testing.test.controller;

import com.testing.test.request.UserRequest;
import com.testing.test.response.UserResponse;
import com.testing.test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> add(@RequestBody @Valid UserRequest userRequest) {
        return userService.add(userRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
