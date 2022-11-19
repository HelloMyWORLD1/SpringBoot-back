package com.helloworld.v1.web.blog.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponse {
    private Boolean success;
    private String message;
    private CommentGetDataDto data;
}
