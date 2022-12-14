package com.helloworld.v1.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helloworld.v1.domain.entity.User;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String field;

    @NotNull
    @Size(min = 11)
    private String phone;

    private String profileImage;

    @Size(min = 8, max = 8)
    private String birth;

    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .field(user.getField())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .birth(user.getBirth())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", field='" + field + '\'' +
                ", phone='" + phone + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", birth='" + birth + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
