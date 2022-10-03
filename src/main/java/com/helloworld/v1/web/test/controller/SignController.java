package com.helloworld.v1.web.test.controller;

import com.helloworld.v1.advice.response.SingleResult;
import com.helloworld.v1.common.config.security.JwtProvider;
import com.helloworld.v1.web.test.dto.UserLoginResponseDto;
import com.helloworld.v1.web.test.dto.UserSignupRequestDto;
import com.helloworld.v1.web.test.service.ResponseService;
import com.helloworld.v1.web.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public SingleResult<String> login(
             @RequestParam String email,
             @RequestParam String password) {
        UserLoginResponseDto userLoginDto = userService.login(email, password);

        String token = jwtProvider.createToken(String.valueOf(userLoginDto.getUserId()), userLoginDto.getRoles());
        return responseService.getSingleResult(token);
    }

    @PostMapping("/signup")
    public SingleResult<Long> signup(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String nickName) {

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickName(nickName)
                .build();
        Long signupId = userService.signup(userSignupRequestDto);
        return responseService.getSingleResult(signupId);
    }
}
