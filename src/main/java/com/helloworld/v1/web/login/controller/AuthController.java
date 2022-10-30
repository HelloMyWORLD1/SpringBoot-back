package com.helloworld.v1.web.login.controller;

import com.helloworld.v1.common.security.jwt.JwtFilter;
import com.helloworld.v1.common.security.jwt.TokenProvider;
import com.helloworld.v1.web.login.dto.LoginCreateResponse;
import com.helloworld.v1.web.login.dto.LoginDto;
import com.helloworld.v1.web.login.dto.TokenDto;
import com.helloworld.v1.web.login.dto.UserDto;
import com.helloworld.v1.web.login.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginCreateResponse> authorize(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));

    }
}