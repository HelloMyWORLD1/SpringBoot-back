package com.helloworld.v1.web.portfolio.controller;

import com.helloworld.v1.web.portfolio.dto.*;
import com.helloworld.v1.web.portfolio.dto.portfolionick.PortfolioGetNicknameResponse;
import com.helloworld.v1.web.portfolio.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portfolio")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Operation(summary = "P4", description = "포트폴리오 생성") // Swagger 표시
    @PostMapping("")
    public ResponseEntity<PortfolioCreateResponse> createPortfolio(
            @Validated @RequestBody PortfolioCreateRequest portfolioCreateRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioCreateRequest, authentication));
    }

    @Operation(summary = "P1", description = "포트폴리오 받기 (메인페이지) 12개") // Swagger 표시
    @GetMapping("/{field}/like")
    public ResponseEntity<PortfolioGetResponse> getPortfolios(@PathVariable("field") String field) {
        return ResponseEntity.ok(portfolioService.getPortfolios(field));
    }

    @Operation(summary = "P2", description = "포트폴리오 받기 OFFSET 20") // Swagger 표시
    @GetMapping("/{field}/latest")
    public ResponseEntity<PortfolioGetLatestResponse> getPortfoliosLatest(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("field") String field) {
        return ResponseEntity.ok(portfolioService.getPortfoliosLatest(page, field));
    }

    @Operation(summary = "P3", description = "개인 포트폴리오") // Swagger 표시
    @GetMapping("/{nickname}")
    public ResponseEntity<PortfolioGetNicknameResponse> getPortfolioByNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(portfolioService.getPortfolioByNickname(nickname));
    }

    @Operation(summary = "PX1", description = "포트폴리오 삭제 (사용 주의)") // Swagger 표시
    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDeleteResponse> deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
        return ResponseEntity.ok(portfolioService.deletePortfolio(portfolioId));
    }
}
