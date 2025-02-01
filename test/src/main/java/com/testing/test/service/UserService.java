package com.testing.test.service;

import com.testing.test.entity.User;
import com.testing.test.exception.TestingException;
import com.testing.test.repository.UserRepository;
import com.testing.test.request.UserRequest;
import com.testing.test.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<UserResponse> add(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new TestingException("User already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword("{noop}"+request.getPassword());

        userRepository.save(user);

        UserResponse response = new UserResponse();
        modelMapper.map(user, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<UserResponse> findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TestingException("User not found"));
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponses);
    }

    public ResponseEntity<UserResponse> update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TestingException("User not found"));
        user.setUsername(request.getUsername());
        user.setPassword("{noop}"+request.getPassword());
        userRepository.save(user);

        UserResponse response = new UserResponse();
        modelMapper.map(user, response);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TestingException("User not found"));
        userRepository.deleteById(user.getId());

        return ResponseEntity.ok("User deleted successfully");
    }
}
