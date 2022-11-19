package com.helloworld.v1.web.blog.controller;

import com.helloworld.v1.web.blog.dto.BlogCreateRequest;
import com.helloworld.v1.web.blog.dto.BlogCreateResponse;
import com.helloworld.v1.web.blog.dto.comment.*;
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

    @Operation(summary = "C2", description = "블로그에 댓글 조회") // Swagger 표시
    @GetMapping("/blog/{blogId}/comments")
    public ResponseEntity<CommentGetResponse> getComments(
            @PathVariable("blogId") Long blogId
    ) {
        return ResponseEntity.ok(commentService.getComments(blogId));
    }

    @Operation(summary = "C3", description = "블로그에 댓글 삭제") // Swagger 표시
    @DeleteMapping("/blog/{blogId}/comment/{commentId}")
    public ResponseEntity<CommentDeleteResponse> deleteComment(
            @PathVariable("blogId") Long blogId,
            @PathVariable("commentId") Long commentId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(commentService.deleteComment(blogId, commentId, authentication));
    }

    @Operation(summary = "C4", description = "블로그에 댓글 수정") // Swagger 표시
    @PutMapping("/blog/{blogId}/comment/{commentId}")
    public ResponseEntity<CommentUpdateResponse> updateComment(
            @PathVariable("blogId") Long blogId,
            @PathVariable("commentId") Long commentId,
            @Validated @RequestBody CommentUpdateRequest commentUpdateRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(commentService.updateComment(blogId, commentId, commentUpdateRequest, authentication));
    }
}
