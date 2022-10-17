package com.helloworld.v1.web.portfolio.controller;

import com.helloworld.v1.web.portfolio.dto.PortfolioCreateRequest;
import com.helloworld.v1.web.portfolio.dto.PortfolioCreateResponse;
import com.helloworld.v1.web.portfolio.dto.PortfolioGetLatestResponse;
import com.helloworld.v1.web.portfolio.dto.PortfolioGetResponse;
import com.helloworld.v1.web.portfolio.dto.portfolionick.PortfolioGetNicknameResponse;
import com.helloworld.v1.web.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "포트폴리오 받기 (메인페이지) || 현재 전체 portfolio가 반환됨") // Swagger 표시
    @GetMapping("/{field}/like")
    public ResponseEntity<PortfolioGetResponse> getPortfolios(@PathVariable("field") String field) {
        return ResponseEntity.ok(portfolioService.getPortfolios(field));
    }

    @Operation(description = "포트폴리오 받기 (메인페이지)") // Swagger 표시
    @GetMapping("/latest")
    public ResponseEntity<PortfolioGetLatestResponse> getPortfoliosLatest(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(portfolioService.getPortfoliosLatest(page));
    }

    @Operation(description = "개인 포트폴리오 || 현재 처음 생성한 portfolio가 반환됨") // Swagger 표시
    @GetMapping("/{nickname}")
    public ResponseEntity<PortfolioGetNicknameResponse> getPortfolioByNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(portfolioService.getPortfolioByNickname(nickname));
    }
}
