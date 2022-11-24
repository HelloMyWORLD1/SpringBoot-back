package com.helloworld.v1.web.auth.dto;

import lombok.Data;

@Data
public class DeleteUserResponse {
    private Boolean success;

    private String message;

    public DeleteUserResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
