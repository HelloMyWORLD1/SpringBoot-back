package com.helloworld.v1.web.login.service;
import java.util.Collections;

import com.helloworld.v1.common.exception.*;
import com.helloworld.v1.common.security.jwt.JwtFilter;
import com.helloworld.v1.common.security.jwt.TokenProvider;
import com.helloworld.v1.domain.entity.Authority;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.common.security.util.SecurityUtil;
import com.helloworld.v1.web.login.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserCreateResponse signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (userRepository.findOneByNickname(userDto.getNickname()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
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
                        .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER))
        );
    }

    public LoginCreateResponse login(LoginDto loginDto){
        String username = getUsernameWithEmail(loginDto.getEmail());

        if(loginDto.getEmail().isEmpty() || loginDto.getPassword().isEmpty()) {
            throw new ApiException(ExceptionEnum.MISSING_REQUIRED_ITEMS);
        }

        UserDto userDto = getUserWithAuthorities(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        ResponseEntity<TokenDto> tokenDtoResponseEntity = new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

        return new LoginCreateResponse(true, "로그인 성공", tokenDtoResponseEntity.getBody(), userDto);
    }
}