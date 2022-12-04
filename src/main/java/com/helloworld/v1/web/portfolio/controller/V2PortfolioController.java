package com.helloworld.v1.web.portfolio.controller;

import com.helloworld.v1.web.portfolio.dto.v1.PortfolioGetResponse;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetCountResponse;
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
}
