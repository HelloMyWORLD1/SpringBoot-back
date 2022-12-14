package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioForeignLanguage {
    @Id
    @GeneratedValue
    private Long id;

    private String foreignLanguage;

    private Long portfolioId; // 유사 FK

    public PortfolioForeignLanguage(String foreignLanguage, Long portfolioId) {
        this.foreignLanguage = foreignLanguage;
        this.portfolioId = portfolioId;
    }

    public void updatePortfolioForeignLanguage(String foreignLanguage, Long portfolioId) {
        this.foreignLanguage = foreignLanguage;
        this.portfolioId = portfolioId;
    }
}
