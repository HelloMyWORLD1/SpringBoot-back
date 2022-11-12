package com.helloworld.v1.web.blog.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.Blog;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.BlogRepository;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.blog.dto.BlogCreateRequest;
import com.helloworld.v1.web.blog.dto.BlogCreateResponse;
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
        // 작성자
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findOneWithAuthoritiesByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_BY_TOKEN);
        }
        // 글 등록
        Blog blog = blogCreateRequest.toEntity(optionalUser.get().getUserId());
        blogRepository.save(blog);
        return new  BlogCreateResponse(true, "블로그 글 등록 성공");
    }
}
