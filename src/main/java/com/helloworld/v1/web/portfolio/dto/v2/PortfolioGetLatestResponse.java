package com.helloworld.v1.web.portfolio.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetLatestResponse {
    private Boolean success;
    private String message;
    private List<PortfolioGetDataDto> data;
}
