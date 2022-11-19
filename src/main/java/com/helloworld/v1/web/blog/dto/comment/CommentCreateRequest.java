package com.helloworld.v1.web.blog.dto.comment;

import com.helloworld.v1.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {
    private String content;

    public Comment toEntity(Long blogId, Long userId) {
        return new Comment(content, blogId, userId);
    }
}
