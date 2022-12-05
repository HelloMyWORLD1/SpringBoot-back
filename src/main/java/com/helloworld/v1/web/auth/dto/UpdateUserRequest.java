package com.helloworld.v1.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String field;

    @NotNull
    @Size(min = 11)
    private String phone;

    @Size(min = 8, max = 8)
    private String birth;

    private String nickname;
}
