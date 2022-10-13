package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioProjectRepository extends JpaRepository<PortfolioProject, Long> {
}