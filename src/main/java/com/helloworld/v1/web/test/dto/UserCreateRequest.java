package com.helloworld.v1.web.test.dto;

import com.helloworld.v1.domain.entity.Sex;
import com.helloworld.v1.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    /**
     * 테스트 용 입니다.
     */
    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPassword;


    private LocalDateTime userCreateAt;


    private LocalDateTime userUpdateAt;


    private String userPhoneNumber;


    private String userNickName;


    private Sex sex;


    private String userProfileUrl;


    public User toEntity() {
        return new User(userEmail, userPassword, userCreateAt, userUpdateAt, userPhoneNumber, userNickName, sex, userProfileUrl);
    }

    public User toEntityTest() {
        return new User(userEmail, userPassword);
    }

}
