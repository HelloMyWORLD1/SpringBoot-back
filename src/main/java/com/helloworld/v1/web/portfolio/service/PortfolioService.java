package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioTechRepository portfolioTechRepository;
    private final PortfolioSnsRepository portfolioSnsRepository;
    private final PortfolioCertificateRepository portfolioCertificateRepository;
    private final PortfolioForeignLanguageRepository portfolioForeignLanguageRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final PortfolioCareerRepository portfolioCareerRepository;

    @Transactional
    public PortfolioCreateResponse createPortfolio(PortfolioCreateRequest portfolioCreateRequest) {
        // portfolio 저장
        Portfolio portfolio = portfolioCreateRequest.toPortfolio(1L);
        Long portfolioId = portfolioRepository.save(portfolio).getId();

        // tech 저장
        List<PortfolioTechDto> techs = portfolioCreateRequest.getTech();
        for (PortfolioTechDto tech : techs) {
            PortfolioTech portfolioTech = tech.toPortfolioTech(portfolioId);
            portfolioTechRepository.save(portfolioTech);
        }

        // sns 저장
        List<String> sns = portfolioCreateRequest.getSns();
        for (String snsEach : sns) {
            PortfolioSns portfolioSns = new PortfolioSns(snsEach, portfolioId);
            portfolioSnsRepository.save(portfolioSns);
        }

        // certificate 저장
        List<String> certificate = portfolioCreateRequest.getCertificate();
        for (String certificateEach : certificate) {
            PortfolioCertificate portfolioCertificate = new PortfolioCertificate(certificateEach, portfolioId);
            portfolioCertificateRepository.save(portfolioCertificate);
        }

        // foreignLanguage 저장
        List<String> foreignLanguage = portfolioCreateRequest.getForeignLanguage();
        for (String foreignLanguageEach : foreignLanguage) {
            PortfolioForeignLanguage portfolioForeignLanguage = new PortfolioForeignLanguage(foreignLanguageEach, portfolioId);
            portfolioForeignLanguageRepository.save(portfolioForeignLanguage);
        }

        // project 저장
        List<PortfolioProjectDto> projects = portfolioCreateRequest.getProject();
        for (PortfolioProjectDto project : projects) {
            PortfolioProject portfolioProject = project.toPortfolioProject(portfolioId);
            portfolioProjectRepository.save(portfolioProject);
        }

        // career 저장
        List<PortfolioCareerDto> careers = portfolioCreateRequest.getCareer();
        for (PortfolioCareerDto career : careers) {
            PortfolioCareer portfolioCareer = career.toPortfolioCareer(portfolioId);
            portfolioCareerRepository.save(portfolioCareer);
        }

        // 성공 반환
        return new PortfolioCreateResponse(true, "포트폴리오 등록 성공");
    }
}
