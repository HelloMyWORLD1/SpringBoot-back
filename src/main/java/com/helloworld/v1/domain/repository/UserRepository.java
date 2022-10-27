package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.User;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 쿼리 수행시 Lazy조회가 아닌 Eager조회를 통한 authorities를 같이 가지고 온다.
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 테스트 용 입니다.
     */
>>>>>>> 35b265c5c2212713a38a0bbb8050f76d8ee526e8
}