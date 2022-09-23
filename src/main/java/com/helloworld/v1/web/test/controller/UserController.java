package com.helloworld.v1.web.test.controller;

import com.helloworld.v1.web.test.dto.UserCreateRequest;
import com.helloworld.v1.web.test.dto.UserCreateResponse;
import com.helloworld.v1.web.test.dto.UsersResponse;
import com.helloworld.v1.web.test.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Test")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class UserController {
    /**
     * 테스트 용 입니다.
     */

    private final UserService userService;

    @Operation(description = "유저 생성") // Swagger 표시
    @PostMapping("/user")
    public ResponseEntity<UserCreateResponse> createUser(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.createUser(userCreateRequest));
    }

    @Operation(description = "전체 유저 조회")
    @GetMapping("/users")
    public ResponseEntity<UsersResponse> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
