package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.*;
import com.helloworld.v1.web.portfolio.dto.portfolionick.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final UserRepository userRepository;

    @Transactional
    public PortfolioCreateResponse createPortfolio(PortfolioCreateRequest portfolioCreateRequest, Authentication authentication) {
        // 작성자
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findOneWithAuthoritiesByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ExceptionEnum.NO_SEARCH_RESOURCE);
        }

        // portfolio 저장
        Portfolio portfolio = portfolioCreateRequest.toPortfolio(optionalUser.get().getUserId());
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

        List<Portfolio> portfolios = portfolioRepository.findTop12ByField(field);
//        List<Portfolio> portfolios = portfolioRepository.findTop12ByOrderByIdDesc();
        List<PortfolioGetDataDto> data = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            User user = userRepository.findById(portfolio.getUserId()).get();
            data.add(new PortfolioGetDataDto(user.getNickname(),
                    portfolio.getDetailJob(),
                    user.getUsername(),
                    field,
                    user.getProfileImage(),
                    portfolio.getTitle(),
                    new ArrayList<>(),
                    new ArrayList<>()
            ));
        }
        return new PortfolioGetResponse(true, "로그인 체크 성공", data);
    }

    public PortfolioGetLatestResponse getPortfoliosLatest(Integer page) {
        long countNum = portfolioRepository.count();
        Integer size = 20;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        PortfolioGetLatestDataDto data = new PortfolioGetLatestDataDto(countNum, getPortfolioSamples(pageRequest));
        return new PortfolioGetLatestResponse(true, "페이지에 따른 데이터 가져오기 성공.", data);
    }

    public PortfolioGetNicknameResponse getPortfolioByNickname(String nickname) {
        // 닉네임에서 userId 조회
        /**
         * 회원 관리 전에 userId = 1L
         */
//        Long userId = 1L;
//        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUserId(userId);
//        if (optionalPortfolio.isEmpty()) {
//            throw new ApiException(ExceptionEnum.NO_SEARCH_RESOURCE);
//        }
        Portfolio portfolio = portfolioRepository.findAll().get(0);
        // 포트폴리오 연관 Entity 조회
//        Portfolio portfolio = optionalPortfolio.get();
        Long portfolioId = portfolio.getId();
        List<String> sns = portfolioSnsRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioSns::getSns).toList();
        List<PortfolioGetNicknameDataTechDto> tech = portfolioTechRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataTechDto(p.getTechName(), p.getContent())).toList();
        List<String> certificate = portfolioCertificateRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioCertificate::getCertificate).toList();
        List<String> foreignLanguage = portfolioForeignLanguageRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioForeignLanguage::getForeignLanguage).toList();
        List<PortfolioGetNicknameDataProjectDto> project = portfolioProjectRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataProjectDto(p.getTitle(), p.getContent())).toList();
        List<PortfolioGetNicknameDataCareerDto> career = portfolioCareerRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataCareerDto(p.getYear(), p.getTitle(), p.getContent())).toList();

        // Response 생성
        PortfolioGetNicknameDataDto data = new PortfolioGetNicknameDataDto(sns, portfolio.getDetailJob(), portfolio.getTitle(), portfolio.getIntroduce(),
                "sample", "sample", tech, portfolio.getEducation(), certificate, foreignLanguage, project, career);
        return new PortfolioGetNicknameResponse(true, "개인 포트폴리오 조회 성공", data);
    }

    /***
     * 이하 공통 Method
     */

    private List<PortfolioGetDataDto> getPortfolioSamples() {
        List<Portfolio> portfolios = portfolioRepository.findTop12ByOrderByIdDesc();
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
