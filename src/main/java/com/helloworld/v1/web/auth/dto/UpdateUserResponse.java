package com.helloworld.v1.web.auth.dto;

import lombok.Data;

@Data
public class UpdateUserResponse {
    private Boolean success;

    private String message;

    public UpdateUserResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
