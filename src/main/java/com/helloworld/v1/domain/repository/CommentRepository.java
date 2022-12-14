package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogId(Long blogId);
}