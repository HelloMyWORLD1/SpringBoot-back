package com.helloworld.v1.web.blog.controller;

import com.helloworld.v1.web.blog.dto.BlogCreateRequest;
import com.helloworld.v1.web.blog.dto.BlogCreateResponse;
import com.helloworld.v1.web.blog.dto.comment.CommentCreateRequest;
import com.helloworld.v1.web.blog.dto.comment.CommentCreateResponse;
import com.helloworld.v1.web.blog.service.BlogService;
import com.helloworld.v1.web.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Blog")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final BlogService blogService;

    @Operation(summary = "C1", description = "블로그에 댓글 등록") // Swagger 표시
    @PostMapping("/blog/{blogId}/comment")
    public ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable("blogId") Long blogId,
            @Validated @RequestBody CommentCreateRequest commentCreateRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(commentService.createComment(blogId, commentCreateRequest, authentication));
    }
}
