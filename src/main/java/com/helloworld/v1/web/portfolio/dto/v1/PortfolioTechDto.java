package com.helloworld.v1.web.portfolio.dto.v1;

import com.helloworld.v1.domain.entity.PortfolioTech;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioTechDto {
    private String techName;
    private String content;

    public PortfolioTech toPortfolioTech(Long portfolioId) {
        return new PortfolioTech(techName, content, portfolioId);
    }
}
