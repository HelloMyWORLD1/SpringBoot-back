package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue
    private Long id;

    private String detailJob;

    private String title;

    private String introduce;

    private String education;

    private Long userId; // 유사 FK

    public Portfolio(String detailJob, String title, String introduce, String education, Long userId) {
        this.detailJob = detailJob;
        this.title = title;
        this.introduce = introduce;
        this.education = education;
        this.userId = userId;
    }

    public void updatePortfolio(String detailJob, String title, String introduce, String education) {
        this.detailJob = detailJob;
        this.title = title;
        this.introduce = introduce;
        this.education = education;
    }
}
