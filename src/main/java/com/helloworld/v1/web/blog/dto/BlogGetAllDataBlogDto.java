package com.helloworld.v1.web.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogGetAllDataBlogDto {
    private Long blogId;
    private String title;
    private String content;
    private String createdAt;
}
