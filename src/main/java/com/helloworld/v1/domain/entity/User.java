package com.helloworld.v1.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * 테스트 용 입니다.
      */

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private Integer age;

    public User(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
