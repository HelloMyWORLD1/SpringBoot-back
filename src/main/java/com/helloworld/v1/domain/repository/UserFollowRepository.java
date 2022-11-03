package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
    Boolean existsByUserIdAndFollowingId(Long userId, Long followingId);
}