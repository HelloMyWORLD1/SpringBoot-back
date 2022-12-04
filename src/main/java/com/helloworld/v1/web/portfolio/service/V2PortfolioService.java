package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.v1.*;
import com.helloworld.v1.web.portfolio.dto.v1.portfolionick.*;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetCountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class V2PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioTechRepository portfolioTechRepository;
    private final PortfolioSnsRepository portfolioSnsRepository;
    private final PortfolioCertificateRepository portfolioCertificateRepository;
    private final PortfolioForeignLanguageRepository portfolioForeignLanguageRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final PortfolioCareerRepository portfolioCareerRepository;
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    public PortfolioGetCountResponse getPortfolioCount(String field) {
        Long countPortfolioByField = portfolioRepository.countPortfolioByField(field);
        return new PortfolioGetCountResponse(countPortfolioByField);
    }
}
