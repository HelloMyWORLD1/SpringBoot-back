package com.helloworld.v1.web.portfolio.dto.portfolionick;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetNicknameDataDto {
    private List<String> sns = new ArrayList<>();
    private String detailJob;
    private String title;
    private String introduce;
    private String profileImage;
    private String field;
    private List<PortfolioGetNicknameDataTechDto> tech = new ArrayList<>();
    private String education;
    private List<String> certificate = new ArrayList<>();
    private List<String> foreignLanguage = new ArrayList<>();
    private List<PortfolioGetNicknameDataProjectDto> project = new ArrayList<>();
    private List<PortfolioGetNicknameDataCareerDto> career = new ArrayList<>();
    private List<String> followings = new ArrayList<>();
    private List<String> followers = new ArrayList<>();

    public PortfolioGetNicknameDataDto(List<String> sns, String detailJob, String title, String introduce, String profileImage, String field, List<PortfolioGetNicknameDataTechDto> tech, String education, List<String> certificate, List<String> foreignLanguage, List<PortfolioGetNicknameDataProjectDto> project, List<PortfolioGetNicknameDataCareerDto> career) {
        this.sns = sns;
        this.detailJob = detailJob;
        this.title = title;
        this.introduce = introduce;
        this.profileImage = profileImage;
        this.field = field;
        this.tech = tech;
        this.education = education;
        this.certificate = certificate;
        this.foreignLanguage = foreignLanguage;
        this.project = project;
        this.career = career;
    }
}
