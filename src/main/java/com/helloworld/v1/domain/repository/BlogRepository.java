package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.Blog;
import com.helloworld.v1.domain.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM blog as b " +
            "RIGHT JOIN user as u " +
            "ON b.user_id = u.user_id " +
            "WHERE u.nickname = :nickname AND b.id IS NOT NULL " +
            "ORDER BY b.id DESC ",
            countQuery = "SELECT count(*) FROM blog as b " +
                    "RIGHT JOIN user as u " +
                    "ON b.user_id = u.user_id " +
                    "WHERE u.nickname = :nickname AND b.id IS NOT NULL ",
            nativeQuery = true
    )
    Page<Blog> findPageByNickname(@Param("nickname") String nickname, Pageable pageable);
}