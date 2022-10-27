package com.helloworld.v1.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCreateResponse {
    private int status;
    private String message;
    private String errors;
    private String token;
    private UserDto user;
}
