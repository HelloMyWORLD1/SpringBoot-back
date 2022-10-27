package com.helloworld.v1.domain.repository;

import com.helloworld.v1.domain.entity.PortfolioCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioCertificateRepository extends JpaRepository<PortfolioCertificate, Long> {
    List<PortfolioCertificate> findAllByPortfolioId(Long portfolioId);
}