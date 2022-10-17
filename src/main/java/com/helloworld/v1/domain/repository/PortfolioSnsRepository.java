package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioSns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioSnsRepository extends JpaRepository<PortfolioSns, Long> {
    List<PortfolioSns> findAllByPortfolioId(Long portfolioId);
}