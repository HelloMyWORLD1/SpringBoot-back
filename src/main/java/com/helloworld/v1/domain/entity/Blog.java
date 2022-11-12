package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private Long userId;

    public Blog(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void updateBlog(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
