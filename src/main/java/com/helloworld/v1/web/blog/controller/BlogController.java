package com.helloworld.v1.web.blog.controller;

import com.helloworld.v1.web.blog.dto.*;
import com.helloworld.v1.web.blog.service.BlogService;
import com.helloworld.v1.web.portfolio.dto.PortfolioCreateRequest;
import com.helloworld.v1.web.portfolio.dto.PortfolioCreateResponse;
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
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogService blogService;

    @Operation(summary = "B1", description = "블로그 글 등록") // Swagger 표시
    @PostMapping("")
    public ResponseEntity<BlogCreateResponse> createBlog(
            @Validated @RequestBody BlogCreateRequest blogCreateRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(blogService.createBlog(blogCreateRequest, authentication));
    }

    @Operation(summary = "B2", description = "블로그 글 삭제") // Swagger 표시
    @DeleteMapping("/{blogId}")
    public ResponseEntity<BlogDeleteResponse> deleteBlog(@PathVariable("blogId") Long blogId, Authentication authentication) {
        return ResponseEntity.ok(blogService.deleteBlog(blogId, authentication));
    }

    @Operation(summary = "B3", description = "블로그 글 수정") // Swagger 표시
    @PutMapping("/{blogId}")
    public ResponseEntity<BlogUpdateResponse> updateBlog(
            @Validated @RequestBody BlogUpdateRequest blogUpdateRequest,
            @PathVariable("blogId") Long blogId,
            Authentication authentication) {
        return ResponseEntity.ok(blogService.updateBlog(blogUpdateRequest, blogId, authentication));
    }

    @Operation(summary = "B4", description = "블로그 글 상세 조회") // Swagger 표시
    @GetMapping("/{blogId}")
    public ResponseEntity<BlogGetResponse> getBlog(@PathVariable("blogId") Long blogId) {
        return ResponseEntity.ok(blogService.getBlog(blogId));
    }
}
