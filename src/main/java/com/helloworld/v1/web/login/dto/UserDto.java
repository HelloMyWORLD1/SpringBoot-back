package com.helloworld.v1.web.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helloworld.v1.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String part;

    @NotNull
    private String phoneNumber;

    private String profileImage;

    private String dateOfBirth;

    @NotNull
    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .part(user.getPart())
                .phoneNumber(user.getPhoneNumber())
                .profileImage(user.getProfileImage())
                .dateOfBirth(user.getDateOfBirth())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
