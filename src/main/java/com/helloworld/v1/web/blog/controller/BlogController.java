package com.helloworld.v1.web.blog.controller;

import com.helloworld.v1.web.blog.dto.BlogCreateRequest;
import com.helloworld.v1.web.blog.dto.BlogCreateResponse;
import com.helloworld.v1.web.blog.dto.BlogDeleteResponse;
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

    @Operation(summary = "B1", description = "블로그 글 등록") // Swagger 표시
    @DeleteMapping("/{blogId}")
    public ResponseEntity<BlogDeleteResponse> deleteBlog(@PathVariable("blogId") Long blogId, Authentication authentication) {
        return ResponseEntity.ok(blogService.deleteBlog(blogId, authentication));
    }
}
