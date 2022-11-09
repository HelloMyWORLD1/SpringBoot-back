package com.helloworld.v1.web.auth.controller;

import com.helloworld.v1.web.auth.dto.LoginCreateResponse;
import com.helloworld.v1.web.auth.dto.LoginDto;
import com.helloworld.v1.web.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    @Operation(summary = "U2", description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginCreateResponse> authorize(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }
}