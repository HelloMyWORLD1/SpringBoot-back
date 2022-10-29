package com.helloworld.v1.web.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioCreateResponse {
    private Boolean success;
    private String message;
}