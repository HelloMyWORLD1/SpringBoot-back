package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserId(Long userId);

    @Query(value = "SELECT * FROM portfolio as p " +
            "RIGHT JOIN user as u " +
            "ON p.user_id = u.user_id " +
            "WHERE u.field = :field AND p.id IS NOT NULL " +
            "ORDER BY p.id DESC ",
            countQuery = "SELECT count(*) FROM portfolio as p " +
                    "RIGHT JOIN user as u " +
                    "ON p.user_id = u.user_id " +
                    "WHERE u.field = :field AND p.id IS NOT NULL ",
            nativeQuery = true
    )
    Page<Portfolio> findPageByField(@Param("field") String field, Pageable pageable);

    @Query(value = "select p.id, p.detail_job, p.education, p.introduce, p.title, p.user_id, u.field, count(uf.user_id) as cnt " +
            "from portfolio as p " +
            "RIGHT JOIN user as u " +
            "ON p.user_id = u.user_id " +
            "LEFT OUTER JOIN user_follow as uf " +
            "ON p.user_id = uf.following_id " +
            "WHERE u.field = :field AND p.id IS NOT NULL " +
            "GROUP BY p.id, p.detail_job, p.education, p.introduce, p.title, p.user_id, u.field, uf.following_id " +
            "ORDER BY cnt DESC " +
            "LIMIT 12"
            , nativeQuery = true)
    List<Portfolio> findTop12ByField(@Param("field") String field);

    @Query(value = "select count(*) " +
            "from portfolio as p " +
            "RIGHT JOIN user as u " +
            "ON p.user_id = u.user_id " +
            "WHERE u.field = :field AND p.id IS NOT NULL "
            , nativeQuery = true)
    Long countPortfolioByField(@Param("field") String field);
}