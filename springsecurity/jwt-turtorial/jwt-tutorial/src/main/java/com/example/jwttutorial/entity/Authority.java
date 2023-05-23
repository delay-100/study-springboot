package com.example.jwttutorial.entity;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id // 권한명(pk)
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}