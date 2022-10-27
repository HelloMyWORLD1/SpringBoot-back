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
public class PortfolioSns {
    @Id
    @GeneratedValue
    private Long id;

    private String sns;

    private Long portfolioId; // 유사 FK

    public PortfolioSns(String sns, Long portfolioId) {
        this.sns = sns;
        this.portfolioId = portfolioId;
    }
}
