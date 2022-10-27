package com.helloworld.v1.web.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetLatestDataDto {
    private Long length;
    private List<PortfolioGetDataDto> array;
}
