package com.helloworld.v1.web.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogGetResponse {
    private Boolean success;
    private String message;
    private BlogGetDto data;
}
