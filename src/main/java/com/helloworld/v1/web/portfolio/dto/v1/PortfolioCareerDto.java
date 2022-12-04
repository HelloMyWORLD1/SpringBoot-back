package com.helloworld.v1.web.portfolio.dto.v1;

import com.helloworld.v1.domain.entity.PortfolioCareer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCareerDto {
    private String year;
    private String title;
    private String content;

    public PortfolioCareer toPortfolioCareer(Long portfolioId) {
        return new PortfolioCareer(year, title, content, portfolioId);
    }
}
