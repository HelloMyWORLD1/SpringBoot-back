package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 테스트 용 입니다.
     */
}