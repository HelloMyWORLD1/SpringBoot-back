package com.helloworld.v1.web.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUnfollowResponse {
    private Boolean success;
    private String message;
}
