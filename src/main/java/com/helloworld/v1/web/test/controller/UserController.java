package com.helloworld.v1.web.test.controller;

import com.helloworld.v1.advice.response.CommonResult;
import com.helloworld.v1.advice.response.ListResult;
import com.helloworld.v1.advice.response.SingleResult;
import com.helloworld.v1.web.test.dto.UserRequestDto;
import com.helloworld.v1.web.test.dto.UserResponseDto;
import com.helloworld.v1.web.test.service.ResponseService;
import com.helloworld.v1.web.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @GetMapping("/user/id/{userId}")
    public SingleResult<UserResponseDto> findUserById
    (@PathVariable Long userId) {
        return responseService.getSingleResult(userService.findById(userId));
    }

    @GetMapping("/user/email/{email}")
    public SingleResult<UserResponseDto> findUserByEmail
            (@PathVariable String email) {
        return responseService.getSingleResult(userService.findByEmail(email));
    }

    @GetMapping("/users")
    public ListResult<UserResponseDto> findAllUser() {
        return responseService.getListResult(userService.findAllUser());
    }

    @PutMapping("/user")
    public SingleResult<Long> update (
            @RequestParam Long userId,
            @RequestParam String nickName) {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .nickName(nickName)
                .build();
        return responseService.getSingleResult(userService.update(userId, userRequestDto));
    }

    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @PathVariable Long userId) {
        userService.delete(userId);
        return responseService.getSuccessResult();
    }
}
