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
public class PortfolioCareer {
    @Id
    @GeneratedValue
    private Long id;

    private String year;

    private String title;

    private String content;

    private Long portfolioId; // 유사 FK

    public PortfolioCareer(String year, String title, String content, Long portfolioId) {
        this.year = year;
        this.title = title;
        this.content = content;
        this.portfolioId = portfolioId;
    }

    public void updatePortfolioCareer(String year, String title, String content, Long portfolioId) {
        this.year = year;
        this.title = title;
        this.content = content;
        this.portfolioId = portfolioId;
    }
}
