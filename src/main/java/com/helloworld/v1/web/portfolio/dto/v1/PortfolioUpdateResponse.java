package com.helloworld.v1.web.portfolio.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioUpdateResponse {
    private Boolean success;
    private String message;
}
