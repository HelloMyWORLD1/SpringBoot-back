package com.helloworld.v1.web.test.controller;

import com.helloworld.v1.web.test.dto.UserCreateRequest;
import com.helloworld.v1.web.test.dto.UserCreateResponse;
import com.helloworld.v1.web.test.dto.UsersResponse;
import com.helloworld.v1.web.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    /**
     * 유저 생성
     * @param userCreateRequest
     */
    @PostMapping("/user")
    public ResponseEntity<UserCreateResponse> createUser(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.createUser(userCreateRequest));
    }

    /**
     * 전체 유저 조회
     */
    @GetMapping("/users")
    public ResponseEntity<UsersResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
