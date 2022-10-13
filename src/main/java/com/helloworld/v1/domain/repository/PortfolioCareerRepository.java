package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioCareerRepository extends JpaRepository<PortfolioCareer, Long> {
}