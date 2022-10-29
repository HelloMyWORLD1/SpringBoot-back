package com.helloworld.v1.web.login.service;
import java.util.Collections;

import com.helloworld.v1.common.exception.DuplicateMemberException;
import com.helloworld.v1.common.exception.NotFoundMemberException;
import com.helloworld.v1.domain.entity.Authority;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.common.security.util.SecurityUtil;
import com.helloworld.v1.web.login.dto.UserCreateResponse;
import com.helloworld.v1.web.login.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserCreateResponse signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .field(userDto.getField())
                .phone(userDto.getPhone())
                .profileImage(userDto.getProfileImage())
                .birth(userDto.getBirth())
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);

        return new UserCreateResponse(true, "회원 가입 성공");
    }

    public String getUsernameWithEmail(String email){
        UserDto userDto = UserDto.from(userRepository.findOneByEmail(email).orElse(null));
        return userDto != null ? userDto.getUsername() : null;
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}