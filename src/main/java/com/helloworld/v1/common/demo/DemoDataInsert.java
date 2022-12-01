package com.helloworld.v1.common.demo;

import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@Component
@RequiredArgsConstructor
@Slf4j
public class DemoDataInsert implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioTechRepository portfolioTechRepository;
    private final PortfolioSnsRepository portfolioSnsRepository;
    private final PortfolioCertificateRepository portfolioCertificateRepository;
    private final PortfolioForeignLanguageRepository portfolioForeignLanguageRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final PortfolioCareerRepository portfolioCareerRepository;
    private final UserFollowRepository userFollowRepository;

    @Override
    public void run(String... args) throws Exception {
        // Default
        List<Authority> authorities = Arrays.asList(
                new Authority("ROLE_USER"),
                new Authority("ROLE_ADMIN")
        );
        authorityRepository.saveAll(authorities);

        /**
         * Demo 1
         */
        // user 저장
        User user = User.builder()
                .email("abc@abc.com")
                .password(passwordEncoder.encode("1234qwer!@#"))
                .username("이의현")
                .field("개발자")
                .phone("01012345678")
                .profileImage("https://helloworld4204.s3.ap-northeast-2.amazonaws.com/default.png")
                .birth("19970419")
                .nickname("xx__sara")
                .authorities(Collections.singleton(new Authority("ROLE_USER")))
                .activated(true)
                .build();
        User savedUser = userRepository.save(user);
        // portfolio 저장
        Portfolio portfolio = new Portfolio("웹 프론트엔드 개발자", "UI / UX에 관심이 많은 프론트엔드 개발자", "~~~~~인 ~~~입니다", "**대학교 졸업", user.getUserId());
        Long portfolioId = portfolioRepository.save(portfolio).getId();

        List<PortfolioTech> portfolioTeches = Arrays.asList(
                new PortfolioTech("Spring", "Spring 공부하고 작동원리에 대해", portfolioId),
                new PortfolioTech("JPA", "JPA 공부하고 작동원리에 대해", portfolioId),
                new PortfolioTech("HTML", "JPA 공부하고 작동원리에 대해", portfolioId)
        );
        portfolioTechRepository.saveAll(portfolioTeches);

        List<PortfolioSns> portfolioSns = Arrays.asList(
                new PortfolioSns("https://github.com/JaeWonyH", portfolioId),
                new PortfolioSns("https://github.com/orgs/HelloMyWORLD1/people/", portfolioId)
        );
        portfolioSnsRepository.saveAll(portfolioSns);

        List<PortfolioCertificate> portfolioCertificates = Arrays.asList(
                new PortfolioCertificate("정처기", portfolioId),
                new PortfolioCertificate("컴활", portfolioId)
        );
        portfolioCertificateRepository.saveAll(portfolioCertificates);

        List<PortfolioForeignLanguage> portfolioForeignLanguages = Arrays.asList(
                new PortfolioForeignLanguage("English", portfolioId),
                new PortfolioForeignLanguage("Spanish", portfolioId)
        );
        portfolioForeignLanguageRepository.saveAll(portfolioForeignLanguages);

        List<PortfolioProject> portfolioProjects = Arrays.asList(
                new PortfolioProject("향수 추천 사이트", "설명임", portfolioId),
                new PortfolioProject("컴퓨터 조립 부품 사이트", "설명임", portfolioId)
        );
        portfolioProjectRepository.saveAll(portfolioProjects);

        List<PortfolioCareer> portfolioCareers = Arrays.asList(
                new PortfolioCareer("2019년-2021년 8월", "**회사", "업무1", portfolioId),
                new PortfolioCareer("2022년-", "**회사", "업무2", portfolioId)
        );
        portfolioCareerRepository.saveAll(portfolioCareers);

        /**
         * Demo 2
         */
        // user 저장
        User user2 = User.builder()
                .email("abc2@abc.com")
                .password(passwordEncoder.encode("1234qwer!@#"))
                .username("이의현2")
                .field("개발자")
                .phone("01012242672")
                .profileImage("https://helloworld4204.s3.ap-northeast-2.amazonaws.com/default.png")
                .birth("19970419")
                .nickname("xx__sara2")
                .authorities(Collections.singleton(new Authority("ROLE_USER")))
                .activated(true)
                .build();
        User savedUser2 = userRepository.save(user2);

        UserFollow userFollow1 = new UserFollow(savedUser.getUserId(), savedUser2.getUserId());
        UserFollow userFollow2 = new UserFollow(savedUser2.getUserId(), savedUser.getUserId());
        userFollowRepository.save(userFollow1);
        userFollowRepository.save(userFollow2);
    }
}
