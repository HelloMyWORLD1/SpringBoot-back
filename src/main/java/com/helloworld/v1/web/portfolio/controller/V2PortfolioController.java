package com.helloworld.v1.web.portfolio.controller;

import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetLatestResponse;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetCountResponse;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetResponse;
import com.helloworld.v1.web.portfolio.service.V2PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Portfolio")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/portfolio")
public class V2PortfolioController {
    private final V2PortfolioService v2PortfolioService;

    @Operation(summary = "R1", description = "분야에 따른 포트폴리오의 개수(길이) 조회") // Swagger 표시
    @GetMapping("/{field}/count")
    public ResponseEntity<PortfolioGetCountResponse> getPortfolioCount(@PathVariable("field") String field) {
        return ResponseEntity.ok(v2PortfolioService.getPortfolioCount(field));
    }

    @Operation(summary = "R2", description = "분야에 따른 포트폴리오의 최신순 데이터 조회 OFFSET 20") // Swagger 표시
    @GetMapping("/{field}/latest")
    public ResponseEntity<PortfolioGetLatestResponse> getPortfoliosLatest(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @PathVariable("field") String field) {
        return ResponseEntity.ok(v2PortfolioService.getPortfoliosLatest(page, field));
    }

    @Operation(summary = "R3", description = "분야에 따른 포트폴리오의 데이터 조회 (팔로워 순) 12개") // Swagger 표시
    @GetMapping("/{field}/like")
    public ResponseEntity<PortfolioGetResponse> getPortfolios(@PathVariable("field") String field) {
        return ResponseEntity.ok(v2PortfolioService.getPortfolios(field));
    }
}
