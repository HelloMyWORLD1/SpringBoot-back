package com.helloworld.v1.web.portfolio.dto;

import com.helloworld.v1.domain.entity.PortfolioProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioProjectDto {
    private String title;
    private String content;

    public PortfolioProject toPortfolioProject(Long portfolioId) {
        return new PortfolioProject(title, content, portfolioId);
    }
}
