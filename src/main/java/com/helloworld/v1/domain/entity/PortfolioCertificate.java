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
public class PortfolioCertificate {
    @Id
    @GeneratedValue
    private Long id;

    private String certificate;

    private Long portfolioId; // 유사 FK

    public PortfolioCertificate(String certificate, Long portfolioId) {
        this.certificate = certificate;
        this.portfolioId = portfolioId;
    }

    public void updatePortfolioCertificate(String certificate, Long portfolioId) {
        this.certificate = certificate;
        this.portfolioId = portfolioId;
    }
}
