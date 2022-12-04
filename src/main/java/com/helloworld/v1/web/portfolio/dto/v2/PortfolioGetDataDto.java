package com.helloworld.v1.web.portfolio.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioGetDataDto {
    private String nickname;
    private String detailJob;
    private String title;
    private String introduce;
    private List<String> followings = new ArrayList<>();
    private List<String> followers = new ArrayList<>();
}
