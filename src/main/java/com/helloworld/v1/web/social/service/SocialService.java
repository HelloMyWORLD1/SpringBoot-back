package com.helloworld.v1.web.social.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.entity.UserFollow;
import com.helloworld.v1.domain.repository.UserFollowRepository;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.social.dto.SocialFollowRequest;
import com.helloworld.v1.web.social.dto.SocialFollowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialService {
    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;

    @Transactional
    public SocialFollowResponse followUser(SocialFollowRequest socialFollowRequest, Authentication authentication) {
        Optional<User> followTargetUser = userRepository.findByNickname(socialFollowRequest.getNickname());
        if (followTargetUser.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_NICKNAME);
        }
        Optional<User> follower = userRepository.findOneWithAuthoritiesByUsername(authentication.getName());
        if (follower.isEmpty()) {
            throw new ApiException(ExceptionEnum.NO_SEARCH_RESOURCE);
        }
        if (userFollowRepository.existsByUserIdAndFollowingId(follower.get().getUserId(), followTargetUser.get().getUserId())) {
            throw new ApiException(ExceptionEnum.ALREADY_PROCESSED);
        }
        UserFollow userFollow = new UserFollow(follower.get().getUserId(), followTargetUser.get().getUserId());
        userFollowRepository.save(userFollow);
        return new SocialFollowResponse(true, "팔로우 성공");
    }
}
