package com.helloworld.v1.web.portfolio.dto.portfolionick;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetNicknameResponse {
    private Boolean success;
    private String message;
    private PortfolioGetNicknameDataDto data;
}
