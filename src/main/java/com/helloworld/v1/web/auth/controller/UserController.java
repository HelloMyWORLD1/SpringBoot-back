package com.helloworld.v1.web.auth.controller;

import com.helloworld.v1.web.auth.dto.*;
import com.helloworld.v1.web.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Operation(summary = "U1", description = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponse> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @Operation(summary = "U4", description = "로그인한 사용자 개인정보 조회")
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }


    @Operation(summary = "U5", description = "계정 삭제")
    @DeleteMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<DeleteUserResponse> deleteUser(){
        return ResponseEntity.ok(userService.deleteUser());
    }

    @Operation(summary = "U6", description = "개인정보 수정")
    @PutMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UpdateUserResponse> updateUser(UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }

    @Operation(summary = "U7", description = "닉네임을 통한 회원 정보 조회")
    @GetMapping("/user/nickname")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<GetUserByNicknameResponse> getUserByNickname(
            @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname
    ){
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }
}