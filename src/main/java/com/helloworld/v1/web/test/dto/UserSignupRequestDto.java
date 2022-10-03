package com.helloworld.v1.web.test.dto;

import com.helloworld.v1.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;

@Getter
public class UserSignupRequestDto {
    private String email;
    private String encodedPassword;
    private String name;
    private String nickName;

    @Builder
    public UserSignupRequestDto(String email, String password, String name, String nickName) {
        this.email = email;
        this.encodedPassword = password;
        this.name = name;
        this.nickName = nickName;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .nickName(nickName)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
