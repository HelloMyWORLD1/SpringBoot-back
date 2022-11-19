package com.helloworld.v1.web.blog.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.Comment;
import com.helloworld.v1.domain.entity.User;
import com.helloworld.v1.domain.repository.BlogRepository;
import com.helloworld.v1.domain.repository.CommentRepository;
import com.helloworld.v1.domain.repository.UserRepository;
import com.helloworld.v1.web.blog.dto.comment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        if (!blogRepository.existsById(blogId)) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_BLOG);
        }
        User user = getUserFromAuthentication(authentication, userRepository);
        Comment comment = commentCreateRequest.toEntity(blogId, user.getUserId());
        commentRepository.save(comment);
        return new CommentCreateResponse(true, "댓글 등록 성공");
    }

    public CommentGetResponse getComments(Long blogId) {
        List<Comment> comments = commentRepository.findAllByBlogId(blogId);
        List<CommentGetDataCommentsDto> commentGetDataCommentsDtos = comments.stream()
                .map(c -> {
                    User user = userRepository.findById(c.getUserId()).get();
                    return new CommentGetDataCommentsDto(c.getId(), user.getNickname(), user.getProfileImage(), c.getContent(), c.getCreatedAt());
                }).toList();
        CommentGetDataDto commentGetDataDto = new CommentGetDataDto((long) commentGetDataCommentsDtos.size(), commentGetDataCommentsDtos);
        return new CommentGetResponse(true, "댓글 조회 성공", commentGetDataDto);
    }

    @Transactional
    public CommentDeleteResponse deleteComment(Long blogId, Long commentId, Authentication authentication) {
        if (!blogRepository.existsById(blogId)) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_BLOG);
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT));
        User user = getUserFromAuthentication(authentication, userRepository);
        if (!user.getUserId().equals(comment.getUserId())) {
            throw new ApiException(ExceptionEnum.NOT_MATCH_NAME);
        }
        commentRepository.delete(comment);
        return new CommentDeleteResponse(true, "댓글 삭제 완료");
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long blogId, Long commentId, CommentUpdateRequest commentUpdateRequest, Authentication authentication) {
        if (!blogRepository.existsById(blogId)) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_BLOG);
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT));
        User user = getUserFromAuthentication(authentication, userRepository);
        if (!user.getUserId().equals(comment.getUserId())) {
            throw new ApiException(ExceptionEnum.NOT_MATCH_NAME);
        }
        comment.updateComment(commentUpdateRequest.getContent());
        return new CommentUpdateResponse(true, "댓글 수정 성공");
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
