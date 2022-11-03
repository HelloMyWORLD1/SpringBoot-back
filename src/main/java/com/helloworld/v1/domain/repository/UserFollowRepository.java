package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    Boolean existsByUserIdAndFollowingId(Long userId, Long followingId);
    Optional<UserFollow> findByUserIdAndFollowingId(Long userId, Long followingId);
}