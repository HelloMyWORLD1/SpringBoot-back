package com.helloworld.v1.web.auth.dto;

import lombok.Data;

@Data
public class GetUserByNicknameResponse {

    private Boolean success;
    private String message;
    private String profileUrl;
    private String username;

    public GetUserByNicknameResponse(Boolean success, String message, String profileUrl, String username) {
        this.success = success;
        this.message = message;
        this.profileUrl = profileUrl;
        this.username = username;
    }
}
