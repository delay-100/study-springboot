package com.example.member.repository;

import com.example.member.domain.Member;

import java.util.List;
import java.util.Optional;

// 회원 리포지토리를 여러가지 방법(JDBC, JPA, MyBatis)로 구현하기위해 인터페이스를 정의함
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
