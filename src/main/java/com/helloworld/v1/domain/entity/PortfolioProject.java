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
public class PortfolioProject {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private Long portfolioId; // 유사 FK

    public PortfolioProject(String title, String content, Long portfolioId) {
        this.title = title;
        this.content = content;
        this.portfolioId = portfolioId;
    }
}
