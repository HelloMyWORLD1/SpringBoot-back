package com.helloworld.v1.web.blog.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDataCommentsDto {
    private Long commentId;
    private String nickname;
    private String profile;
    private String content;
    private String createdAt;
}
