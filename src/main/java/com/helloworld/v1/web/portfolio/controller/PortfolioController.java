package com.helloworld.v1.web.portfolio.controller;

import com.helloworld.v1.web.portfolio.dto.PortfolioCreateRequest;
import com.helloworld.v1.web.portfolio.dto.PortfolioCreateResponse;
import com.helloworld.v1.web.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Portfolio")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Operation(description = "포트폴리오 생성") // Swagger 표시
    @PostMapping("")
    public ResponseEntity<PortfolioCreateResponse> createPortfolio(@Validated @RequestBody PortfolioCreateRequest portfolioCreateRequest) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioCreateRequest));
    }
}
