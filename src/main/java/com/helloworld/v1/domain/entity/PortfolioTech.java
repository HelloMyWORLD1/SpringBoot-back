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
public class PortfolioTech {
    @Id
    @GeneratedValue
    private Long id;

    private String techName;

    private String content;

    private Long portfolioId; // 유사 FK

    public PortfolioTech(String techName, String content, Long portfolioId) {
        this.techName = techName;
        this.content = content;
        this.portfolioId = portfolioId;
    }

    public void updatePortfolioTech(String techName, String content, Long portfolioId) {
        this.techName = techName;
        this.content = content;
        this.portfolioId = portfolioId;
    }
}
