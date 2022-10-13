package com.helloworld.v1.web.portfolio.dto;

import com.helloworld.v1.domain.entity.Portfolio;
import com.helloworld.v1.domain.entity.PortfolioProject;
import com.helloworld.v1.domain.entity.PortfolioTech;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreateRequest {
    @NotNull
    private String detailJob;
    @NotNull
    private String title;
    @NotNull
    private String introduce;

    private List<PortfolioTechDto> tech = new ArrayList<>();
    private String education;
    private List<String> sns = new ArrayList<>();
    private List<String> certificate = new ArrayList<>();
    private List<String> foreignLanguage = new ArrayList<>();
    private List<PortfolioProjectDto> project = new ArrayList<>();
    private List<PortfolioCareerDto> career = new ArrayList<>();

    public Portfolio toPortfolio(Long userId) {
        return new Portfolio(
                detailJob,
                title,
                introduce,
                education,
                userId
        );
    }
}
