package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public PortfolioGetResponse getPortfolios(String field) {
        /*
        회원관리 전까지 field가 없으므로 전체 조회
         */
        List<PortfolioGetDataDto> data = getPortfolioSamples();
        return new PortfolioGetResponse(true, "로그인 체크 성공", data);
    }

    public PortfolioGetLatestResponse getPortfoliosLatest(Integer page) {
        long countNum = portfolioRepository.count();
        Integer size = 20;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        PortfolioGetLatestDataDto data = new PortfolioGetLatestDataDto(countNum, getPortfolioSamples(pageRequest));
        return new PortfolioGetLatestResponse(true, "페이지에 따른 데이터 가져오기 성공.", data);
    }

    /***
     * 이하 공통 Method
     */

    private List<PortfolioGetDataDto> getPortfolioSamples() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<PortfolioGetDataDto> data = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            String tempNickname = "helloMin";
            String tempName = "이의현";
            String tempField = "개발자";
            String tempProfileImage = "test";
            data.add(new PortfolioGetDataDto(tempNickname,
                    portfolio.getDetailJob(),
                    tempName,
                    tempField,
                    tempProfileImage,
                    portfolio.getTitle(),
                    new ArrayList<>(),
                    new ArrayList<>()
            ));
        }
        return data;
    }

    private List<PortfolioGetDataDto> getPortfolioSamples(PageRequest pageRequest) {
        Page<Portfolio> portfolioPage = portfolioRepository.findAll(pageRequest);
        List<Portfolio> portfolios = portfolioPage.getContent();
        List<PortfolioGetDataDto> data = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            String tempNickname = "helloMin";
            String tempName = "이의현";
            String tempField = "개발자";
            String tempProfileImage = "test";
            data.add(new PortfolioGetDataDto(tempNickname,
                    portfolio.getDetailJob(),
                    tempName,
                    tempField,
                    tempProfileImage,
                    portfolio.getTitle(),
                    new ArrayList<>(),
                    new ArrayList<>()
            ));
        }
        return data;
    }
}
