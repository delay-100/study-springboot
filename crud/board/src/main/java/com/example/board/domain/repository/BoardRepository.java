package com.example.board.domain.repository;

import com.example.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository는 데이터 조작을 담당하며, JpaRepository를 상속
public interface BoardRepository extends JpaRepository<Board, Long> { // JpaRepository 값 -  Board: 매핑할 엔터티, Long: 데이터타입
}