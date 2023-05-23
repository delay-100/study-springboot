package com.example.jwttutorial.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Entity // 데이터베이스와 1:1 매핑되는 객체
@Table(name = "users") // table명을 user로 설정
@Getter
@Setter
@Builder
@AllArgsConstructor // 생성자 코드 자동 생성
@NoArgsConstructor
public class User {

    @Id // pk
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany // user:user_authority = 1 : N, user_authoirty : authority= N:N 관계
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}