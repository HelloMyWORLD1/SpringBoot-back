package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserFollow {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId; // follower (= fan)

    private Long followingId; // follow target

    public UserFollow(Long userId, Long followingId) {
        this.userId = userId;
        this.followingId = followingId;
    }
}
