package com.helloworld.v1.web.test.dto;

import com.helloworld.v1.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    /**
     * 테스트 용 입니다.
     */

    @NotBlank
    private String username;

    @NotNull
    private Integer age;

    public User toEntity() {
        return new User(username, age);
    }
}
