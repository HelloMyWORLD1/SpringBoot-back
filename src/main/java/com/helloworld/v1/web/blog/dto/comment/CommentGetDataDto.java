package com.helloworld.v1.web.blog.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDataDto {
    private Long length;
    private List<CommentGetDataCommentsDto> comments;
}
