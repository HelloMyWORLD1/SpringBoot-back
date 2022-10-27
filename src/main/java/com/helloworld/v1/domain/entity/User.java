package com.helloworld.v1.domain.entity;

<<<<<<< HEAD
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "field")
    private String field;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "birth")
    private String birth;

    @Column(name = "nickname")
    private String nickname;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
=======
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
>>>>>>> 35b265c5c2212713a38a0bbb8050f76d8ee526e8
