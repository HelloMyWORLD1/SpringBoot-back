package com.helloworld.v1.web.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    /**
     * 테스트 용 입니다.
     */

    private List<UserDto> users = new ArrayList<>();
}
