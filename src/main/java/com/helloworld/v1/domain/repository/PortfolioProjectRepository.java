package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, Long> {
    List<PortfolioProject> findAllByPortfolioId(Long portfolioId);
}