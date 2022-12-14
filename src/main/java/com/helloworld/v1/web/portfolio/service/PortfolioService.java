package com.helloworld.v1.web.portfolio.service;

import com.helloworld.v1.common.exception.ApiException;
import com.helloworld.v1.common.exception.ExceptionEnum;
import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.web.portfolio.dto.v1.*;
import com.helloworld.v1.web.portfolio.dto.v1.portfolionick.*;
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
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioTechRepository portfolioTechRepository;
    private final PortfolioSnsRepository portfolioSnsRepository;
    private final PortfolioCertificateRepository portfolioCertificateRepository;
    private final PortfolioForeignLanguageRepository portfolioForeignLanguageRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final PortfolioCareerRepository portfolioCareerRepository;
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    @Transactional
    public PortfolioCreateResponse createPortfolio(PortfolioCreateRequest portfolioCreateRequest, Authentication authentication) {
        // 작성자
        String username = authentication.getName();
        Optional<User> optionalUser = userRepository.findOneWithAuthoritiesByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER_BY_TOKEN);
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
        List<PortfolioGetDataDto> data = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            User user = userRepository.findById(portfolio.getUserId()).get();
            data.add(new PortfolioGetDataDto(user.getNickname(),
                    portfolio.getDetailJob(),
                    user.getUsername(),
                    field,
                    user.getProfileImage(),
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
                    user.getUsername(),
                    user.getField(),
                    user.getProfileImage(),
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
        PortfolioGetLatestDataDto data = new PortfolioGetLatestDataDto(totalElements, portfolioGetDataDtos);
        return new PortfolioGetLatestResponse(true, "페이지에 따른 데이터 가져오기 성공.", data);
    }

    public PortfolioGetNicknameResponse getPortfolioByNickname(String nickname) {
        // 닉네임에서 userId 조회
        Optional<User> byNickname = userRepository.findByNickname(nickname);
        if (byNickname.isEmpty()) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_NICKNAME);
        }
        User user = byNickname.get();
        // 포트폴리오 연관 Entity 조회
        Portfolio portfolio = portfolioRepository.findByUserId(user.getUserId()).get();
        Long portfolioId = portfolio.getId();
        List<String> sns = portfolioSnsRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioSns::getSns).toList();
        List<PortfolioGetNicknameDataTechDto> tech = portfolioTechRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataTechDto(p.getTechName(), p.getContent())).toList();
        List<String> certificate = portfolioCertificateRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioCertificate::getCertificate).toList();
        List<String> foreignLanguage = portfolioForeignLanguageRepository.findAllByPortfolioId(portfolioId).stream().map(PortfolioForeignLanguage::getForeignLanguage).toList();
        List<PortfolioGetNicknameDataProjectDto> project = portfolioProjectRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataProjectDto(p.getTitle(), p.getContent())).toList();
        List<PortfolioGetNicknameDataCareerDto> career = portfolioCareerRepository.findAllByPortfolioId(portfolioId).stream().map(p -> new PortfolioGetNicknameDataCareerDto(p.getYear(), p.getTitle(), p.getContent())).toList();

        // Response 생성
        PortfolioGetNicknameDataDto data = new PortfolioGetNicknameDataDto(sns, portfolio.getDetailJob(), portfolio.getTitle(), portfolio.getIntroduce(),
                user.getProfileImage(), user.getField(), tech, portfolio.getEducation(), certificate, foreignLanguage, project, career,
                userFollowRepository.findAllByUserId(user.getUserId()).stream()
                        .map(u -> userRepository.findById(u.getFollowingId()).get().getNickname())
                        .distinct().collect(Collectors.toList()),
                userFollowRepository.findAllByFollowingId(user.getUserId()).stream()
                        .map(u -> userRepository.findById(u.getUserId()).get().getNickname())
                        .distinct().collect(Collectors.toList()));
        return new PortfolioGetNicknameResponse(true, "개인 포트폴리오 조회 성공", data);
    }

    @Transactional
    public PortfolioDeleteResponse deletePortfolio(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_USER_BY_TOKEN)
        );
        Portfolio portfolio = portfolioRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new ApiException(ExceptionEnum.NO_SEARCH_RESOURCE)
        );
        Long portfolioId = portfolio.getId();

        List<PortfolioCareer> portfolioCareers = portfolioCareerRepository.findAllByPortfolioId(portfolioId);
        portfolioCareerRepository.deleteAll(portfolioCareers);
        List<PortfolioCertificate> portfolioCertificates = portfolioCertificateRepository.findAllByPortfolioId(portfolioId);
        portfolioCertificateRepository.deleteAll(portfolioCertificates);
        List<PortfolioForeignLanguage> portfolioForeignLanguages = portfolioForeignLanguageRepository.findAllByPortfolioId(portfolioId);
        portfolioForeignLanguageRepository.deleteAll(portfolioForeignLanguages);
        List<PortfolioProject> portfolioProjects = portfolioProjectRepository.findAllByPortfolioId(portfolioId);
        portfolioProjectRepository.deleteAll(portfolioProjects);
        List<PortfolioSns> portfolioSns = portfolioSnsRepository.findAllByPortfolioId(portfolioId);
        portfolioSnsRepository.deleteAll(portfolioSns);
        List<PortfolioTech> portfolioTeches = portfolioTechRepository.findAllByPortfolioId(portfolioId);
        portfolioTechRepository.deleteAll(portfolioTeches);

        portfolioRepository.deleteById(portfolioId);
        return new PortfolioDeleteResponse(true, "포트폴리오 삭제 완료");
    }

    @Transactional
    public PortfolioUpdateResponse updatePortfolio(PortfolioCreateRequest portfolioCreateRequest, Authentication authentication) {
        deletePortfolio(authentication);
        createPortfolio(portfolioCreateRequest, authentication);
        return new PortfolioUpdateResponse(true, "포트폴리오 수정 완료");
    }
}
