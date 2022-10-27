package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioTechRepository extends JpaRepository<PortfolioTech, Long> {
    List<PortfolioTech> findAllByPortfolioId(Long portfolioId);
}