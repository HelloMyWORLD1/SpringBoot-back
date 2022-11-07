package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUserId(Long userId);
    List<Portfolio> findTop12ByOrderByIdDesc();

    @Query(value = "select * from portfolio as p " +
            "RIGHT JOIN user as u " +
            "ON p.user_id = u.user_id " +
            "WHERE u.field = :field AND p.id IS NOT NULL " +
            "ORDER BY p.id DESC " +
            "LIMIT 12"
            , nativeQuery = true)
    List<Portfolio> findTop12ByField(@Param("field") String field);
}