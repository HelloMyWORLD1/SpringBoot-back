package com.helloworld.v1.web.blog.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.Comment;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.BlogRepository;
import com.helloworld.v1.domain.repository.CommentRepository;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.blog.dto.comment.CommentCreateRequest;
import com.helloworld.v1.web.blog.dto.comment.CommentCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse createComment(Long blogId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        User user = userRepository.findByNickname(authentication.getName()).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_USER_BY_TOKEN));
        Comment comment = commentCreateRequest.toEntity(blogId, user.getUserId());
        commentRepository.save(comment);
        return new CommentCreateResponse(true, "댓글 등록 성공");
    }
}
