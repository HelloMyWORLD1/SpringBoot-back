package com.helloworld.v1.web.blog.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.Blog;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.BlogRepository;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.blog.dto.*;
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
public class BlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Transactional
    public BlogCreateResponse createBlog(BlogCreateRequest blogCreateRequest, Authentication authentication) {
        User user = getUserFromAuthentication(authentication, userRepository);
        Blog blog = blogCreateRequest.toEntity(user.getUserId());
        blogRepository.save(blog);
        return new  BlogCreateResponse(true, "블로그 글 등록 성공");
    }


    @Transactional
    public BlogDeleteResponse deleteBlog(Long blogId, Authentication authentication) {
        User user = getUserFromAuthentication(authentication, userRepository);
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if (optionalBlog.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_BLOG);
        }
        if (!user.getUserId().equals(optionalBlog.get().getUserId())) {
            throw new ApiException(ExceptionEnum.NOT_MATCH_NAME); // 권한없음
        }
        blogRepository.delete(optionalBlog.get());
        return new BlogDeleteResponse(true, "게시글 삭제 완료");
    }

    @Transactional
    public BlogUpdateResponse updateBlog(BlogUpdateRequest blogUpdateRequest, Long blogId, Authentication authentication) {
        User user = getUserFromAuthentication(authentication, userRepository);
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_BLOG));
        if (!blog.getUserId().equals(user.getUserId())) {
            throw new ApiException(ExceptionEnum.NOT_MATCH_NAME);
        }
        blog.updateBlog(blogUpdateRequest.getTitle(), blogUpdateRequest.getContent());
        return new BlogUpdateResponse(true, "게시글 수정 성공");
    }

    public BlogGetResponse getBlog(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_BLOG));
        BlogGetDto blogGetDto = new BlogGetDto(blogId, blog.getTitle(), blog.getContent());
        return new BlogGetResponse(true, "게시글 조회 성공", blogGetDto);
    }


    /**
     * 공통 Method
     * Authentication 에서 User 반환
     * User 없으면 에러
     */
    private User getUserFromAuthentication(Authentication authentication, UserRepository userRepository) {
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findOneWithAuthoritiesByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_BY_TOKEN);
        }
        return optionalUser.get();
    }



}
