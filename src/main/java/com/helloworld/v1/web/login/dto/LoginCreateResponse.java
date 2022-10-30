package com.helloworld.v1.web.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCreateResponse {
    private Boolean success;

    private String message;

    private TokenDto tokenDto;

    private UserDto userDto;
}