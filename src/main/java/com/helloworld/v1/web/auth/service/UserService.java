package com.helloworld.v1.web.auth.service;
import java.util.Collections;
import java.util.Objects;

import com.helloworld.v1.common.exception.*;
import com.helloworld.v1.common.security.jwt.JwtFilter;
import com.helloworld.v1.common.security.jwt.TokenProvider;
import com.helloworld.v1.domain.entity.Authority;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.common.security.util.SecurityUtil;
import com.helloworld.v1.web.auth.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserCreateResponse signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (userRepository.findByNickname(userDto.getNickname()).orElse(null) != null) {
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
        user.setUsername(user.getUsername() + "#" + user.getUserId());

        return new UserCreateResponse(true, "회원 가입 성공");
    }

    public String getUsernameWithEmail(String email){
        UserDto userDto = UserDto.from(userRepository.findOneByEmail(email).orElse(null));
        return userDto != null ? userDto.getUsername() : null;
    }

    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

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

        if(userRepository.findOneWithAuthoritiesByUsername(username).isEmpty()){
            throw new ApiException(ExceptionEnum.NOT_FOUND_EMAIL);
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

        if (tokenDtoResponseEntity.getBody().getToken().isEmpty()) {
            throw new ApiException(ExceptionEnum.TOKEN_EMPTY);
        }

        return new LoginCreateResponse(true, "로그인 성공", tokenDtoResponseEntity.getBody(), userDto);
    }

    @Transactional
    public DeleteUserResponse deleteUser(){
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        userRepository.delete(findUser);
        return new DeleteUserResponse(true, "계정 삭제 완료");
    }

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        if (!Objects.equals(updateUserRequest.getEmail(), findUser.getEmail()) &&
                userRepository.findOneByEmail(updateUserRequest.getEmail()).orElse(null) != null)  {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (!Objects.equals(updateUserRequest.getNickname(), findUser.getNickname()) &&
                userRepository.findByNickname(updateUserRequest.getNickname()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
        }



        findUser.update(
                updateUserRequest.getEmail(),
                passwordEncoder.encode(updateUserRequest.getPassword()),
                updateUserRequest.getField(),
                updateUserRequest.getPhone(),
                updateUserRequest.getBirth(),
                updateUserRequest.getNickname()
        );

        return new UpdateUserResponse(true, "개인 정보 수정이 완료 되었습니다");
    }
}