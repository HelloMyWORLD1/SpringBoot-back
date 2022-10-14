package com.helloworld.v1.web.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetResponse {
    private Boolean success;
    private String message;
    private List<PortfolioGetDataDto> data = new ArrayList<>();
}
