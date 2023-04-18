package com.example.member.domain;
// db 테이블에 값을 저장할 값 또는 읽어온 값을 저장하는 데 사용
// db 테이블들과 동일한 구조로 정의함

import org.springframework.context.annotation.Bean;

public class Member {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
