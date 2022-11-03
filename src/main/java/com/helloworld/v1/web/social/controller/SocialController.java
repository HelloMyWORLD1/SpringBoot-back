package com.helloworld.v1.web.social.controller;

import com.helloworld.v1.web.portfolio.dto.PortfolioCreateRequest;
import com.helloworld.v1.web.portfolio.dto.PortfolioCreateResponse;
import com.helloworld.v1.web.social.dto.SocialFollowRequest;
import com.helloworld.v1.web.social.dto.SocialFollowResponse;
import com.helloworld.v1.web.social.dto.SocialUnfollowRequest;
import com.helloworld.v1.web.social.dto.SocialUnfollowResponse;
import com.helloworld.v1.web.social.service.SocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Social")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social")
public class SocialController {
    private final SocialService socialService;

    @Operation(description = "User 팔로우")
    @PostMapping("/follow")
    public ResponseEntity<SocialFollowResponse> followUser(
            @Validated @RequestBody SocialFollowRequest socialFollowRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(socialService.followUser(socialFollowRequest, authentication));
    }

    @Operation(description = "User 팔로우")
    @PostMapping("/unfollow")
    public ResponseEntity<SocialUnfollowResponse> unfollowUser(
            @Validated @RequestBody SocialUnfollowRequest socialUnfollowRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(socialService.unfollowUser(socialUnfollowRequest, authentication));
    }
}
