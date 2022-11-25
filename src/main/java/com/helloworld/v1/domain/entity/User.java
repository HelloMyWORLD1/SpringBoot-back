package com.helloworld.v1.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void update(String email, String password, String field, String phone, String birth, String nickname) {
        this.email = email;
        this.password = password;
        this.field = field;
        this.phone = phone;
        this.birth = birth;
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}