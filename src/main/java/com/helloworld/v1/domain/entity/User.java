package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User{

    /**
     * 테스트 용 입니다.
      */

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;

    @NotBlank
    @Column(name = "USER_EMAIL", nullable = false, length = 100, unique = true)
    private String userEmail;

    @NotBlank
    @Column(name = "USER_PASSWORD")
    private String userPassword;

//    @NotBlank
    @Column(name = "USER_CREATE_AT")
    private LocalDateTime userCreateAt;

//    @NotBlank
    @Column(name = "USER_UPDATE_AT")
    private LocalDateTime userUpdateAt;

//    @NotBlank
    @Column(name = "USER_PHONE_NUMBER")
    private String userPhoneNumber;

//    @NotBlank
    @Column(name = "USER_NAME")
    private String userName;

//    @NotBlank
    @Column(name = "USER_SEX")
    private Sex userSex;

//    @NotBlank
    @Column(name = "USER_PROFILE_URL")
    private String userProfileUrl;

    public User(String userEmail, String userPassword, LocalDateTime userCreateAt,
                LocalDateTime userUpdateAt, String userPhoneNumber, String userName,
                Sex userSex, String userProfileUrl) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userCreateAt = userCreateAt;
        this.userUpdateAt = userUpdateAt;
        this.userPhoneNumber = userPhoneNumber;
        this.userName = userName;
        this.userSex = userSex;
        this.userProfileUrl = userProfileUrl;
    }

    public User(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }
}
