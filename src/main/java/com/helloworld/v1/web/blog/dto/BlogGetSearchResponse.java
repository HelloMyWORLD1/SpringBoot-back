package com.helloworld.v1.web.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogGetSearchResponse {
    private Boolean success;
    private String message;
    private List<BlogGetSearchDto> data;
}
