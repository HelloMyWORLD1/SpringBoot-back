package com.helloworld.v1.web.auth.service;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.helloworld.v1.common.exception.*;
import com.helloworld.v1.common.security.jwt.JwtFilter;
import com.helloworld.v1.common.security.jwt.TokenProvider;
import com.helloworld.v1.domain.entity.*;
import com.helloworld.v1.domain.repository.*;
import com.helloworld.v1.common.security.util.SecurityUtil;
import com.helloworld.v1.web.auth.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioCareerRepository portfolioCareerRepository;
    private final PortfolioSnsRepository portfolioSnsRepository;
    private final PortfolioForeignLanguageRepository portfolioForeignLanguageRepository;
    private final PortfolioTechRepository portfolioTechRepository;
    private final PortfolioCertificateRepository portfolioCertificateRepository;
    private final PortfolioProjectRepository portfolioProjectRepository;
    private final BlogRepository blogRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserCreateResponse signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (userRepository.findByNickname(userDto.getNickname()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .field(userDto.getField())
                .phone(userDto.getPhone())
                .profileImage("https://helloworld4204.s3.ap-northeast-2.amazonaws.com/default.png")
                .birth(userDto.getBirth())
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        userRepository.save(user);
        user.setUsername(user.getUsername() + "#" + user.getUserId());

        return new UserCreateResponse(true, "?????? ?????? ??????");
    }

    public String getUsernameWithEmail(String email){
        UserDto userDto = UserDto.from(userRepository.findOneByEmail(email).orElse(null));
        return userDto != null ? userDto.getUsername() : null;
    }

    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER))
        );
    }

    public LoginCreateResponse login(LoginDto loginDto){
        String username = getUsernameWithEmail(loginDto.getEmail());

        if(loginDto.getEmail().isEmpty() || loginDto.getPassword().isEmpty()) {
            throw new ApiException(ExceptionEnum.MISSING_REQUIRED_ITEMS);
        }

        if(userRepository.findOneWithAuthoritiesByUsername(username).isEmpty()){
            throw new ApiException(ExceptionEnum.NOT_FOUND_EMAIL);
        }

        UserDto userDto = getUserWithAuthorities(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        ResponseEntity<TokenDto> tokenDtoResponseEntity = new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);

        if (tokenDtoResponseEntity.getBody().getToken().isEmpty()) {
            throw new ApiException(ExceptionEnum.TOKEN_EMPTY);
        }

        return new LoginCreateResponse(true, "????????? ??????", tokenDtoResponseEntity.getBody(), userDto);
    }

    @Transactional
    public DeleteUserResponse deleteUser(){
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        Portfolio findPortfolio = portfolioRepository.findByUserId(findUser.getUserId()).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));
        Long portfolioId = findPortfolio.getId();
        List<Blog> BlogAllByUserId = blogRepository.findAllByUserId(findUser.getUserId());

        List<PortfolioCareer> careerAllByPortfolioId = portfolioCareerRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioSns> snsAllByPortfolioId = portfolioSnsRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioForeignLanguage> LanguageAllByPortfolioId = portfolioForeignLanguageRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioTech> techAllByPortfolioId = portfolioTechRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioCertificate> CertificateAllByPortfolioId = portfolioCertificateRepository.findAllByPortfolioId(portfolioId);
        List<PortfolioProject> projectAllByPortfolioId = portfolioProjectRepository.findAllByPortfolioId(portfolioId);


        userRepository.delete(findUser);
        portfolioRepository.delete(findPortfolio);
        for (Blog blog : BlogAllByUserId) {
            blogRepository.delete(blog);
        }
        for (PortfolioCareer portfolioCareer : careerAllByPortfolioId) {
            portfolioCareerRepository.delete(portfolioCareer);
        }
        for (PortfolioSns portfolioSns : snsAllByPortfolioId) {
            portfolioSnsRepository.delete(portfolioSns);
        }
        for (PortfolioForeignLanguage portfolioForeignLanguage : LanguageAllByPortfolioId) {
            portfolioForeignLanguageRepository.delete(portfolioForeignLanguage);
        }
        for (PortfolioTech portfolioTech : techAllByPortfolioId) {
            portfolioTechRepository.delete(portfolioTech);
        }
        for (PortfolioCertificate portfolioCertificate : CertificateAllByPortfolioId) {
            portfolioCertificateRepository.delete(portfolioCertificate);
        }
        for (PortfolioProject portfolioProject : projectAllByPortfolioId) {
            portfolioProjectRepository.delete(portfolioProject);
        }

        return new DeleteUserResponse(true, "?????? ?????? ??????");
    }

    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User findUser = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_MEMBER));

        if (!Objects.equals(updateUserRequest.getEmail(), findUser.getEmail()) &&
                userRepository.findOneByEmail(updateUserRequest.getEmail()).orElse(null) != null)  {
            throw new ApiException(ExceptionEnum.DUPLICATE_EMAIL);
        }

        if (!Objects.equals(updateUserRequest.getNickname(), findUser.getNickname()) &&
                userRepository.findByNickname(updateUserRequest.getNickname()).orElse(null) != null) {
            throw new ApiException(ExceptionEnum.DUPLICATE_NICKNAME);
        }



        findUser.update(
                updateUserRequest.getEmail(),
                updateUserRequest.getField(),
                updateUserRequest.getPhone(),
                updateUserRequest.getBirth(),
                updateUserRequest.getNickname()
        );

        return new UpdateUserResponse(true, "?????? ?????? ????????? ?????? ???????????????");
    }

    public GetUserByNicknameResponse getUserByNickname(String nickname) {
        User findUser = userRepository.findByNickname(nickname).orElseThrow(() -> new ApiException(ExceptionEnum.NOT_FOUND_NICKNAME));
        String profileUrl = findUser.getProfileImage();
        String username = findUser.getUsername();
        return new GetUserByNicknameResponse(true, "???????????? ?????? ?????? ?????? ?????? ??????", profileUrl, username);

    }
}