package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetCountResponse;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetDataDto;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetLatestResponse;
import com.helloworld.v1.web.portfolio.dto.v2.PortfolioGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    public PortfolioGetLatestResponse getPortfoliosLatest(Integer page, String field) {
        Integer size = 20;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Portfolio> portfolioPage = portfolioRepository.findPageByField(field, pageRequest);
        List<Portfolio> portfolios = portfolioPage.getContent();
        long totalElements = portfolioPage.getTotalElements();
        List<PortfolioGetDataDto> portfolioGetDataDtos = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            User user = userRepository.findById(portfolio.getUserId()).get();
            portfolioGetDataDtos.add(new PortfolioGetDataDto(user.getNickname(),
                    portfolio.getDetailJob(),
                    portfolio.getTitle(),
                    portfolio.getIntroduce(),
                    userFollowRepository.findAllByUserId(user.getUserId()).stream()
                            .map(u -> userRepository.findById(u.getFollowingId()).get().getNickname())
                            .distinct().collect(Collectors.toList()),
                    userFollowRepository.findAllByFollowingId(user.getUserId()).stream()
                            .map(u -> userRepository.findById(u.getUserId()).get().getNickname())
                            .distinct().collect(Collectors.toList())
            ));
        }
        return new PortfolioGetLatestResponse(true, "페이지에 따른 데이터 가져오기 성공.", portfolioGetDataDtos);
    }

    public PortfolioGetResponse getPortfolios(String field) {
        List<Portfolio> portfolios = portfolioRepository.findTop12ByField(field);
        List<PortfolioGetDataDto> data = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            User user = userRepository.findById(portfolio.getUserId()).get();
            data.add(new PortfolioGetDataDto(user.getNickname(),
                    portfolio.getDetailJob(),
                    portfolio.getTitle(),
                    portfolio.getIntroduce(),
                    userFollowRepository.findAllByUserId(user.getUserId()).stream()
                            .map(u -> userRepository.findById(u.getFollowingId()).get().getNickname())
                            .distinct().collect(Collectors.toList()),
                    userFollowRepository.findAllByFollowingId(user.getUserId()).stream()
                            .map(u -> userRepository.findById(u.getUserId()).get().getNickname())
                            .distinct().collect(Collectors.toList())
            ));
        }
        return new PortfolioGetResponse(true, "로그인 체크 성공", data);
    }
}
