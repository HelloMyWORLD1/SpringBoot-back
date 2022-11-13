package com.helloworld.v1.web.blog.dto;

import com.helloworld.v1.domain.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequest {
    private String title;
    private String content;

    public Blog toEntity(Long userId) {
        return new Blog(title, content, userId);
    }
}
