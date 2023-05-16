package com.example.bookmybatis.repository;

import com.example.bookmybatis.Entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface MybatisBookRepository {
    void save(BookEntity bookEntity);

    List<BookEntity> findAll();

    List<BookEntity> findCond(BookEntity bookEntity);
    Optional<BookEntity> findById(Long bookId);

    void update(BookEntity bookEntity);
    void delete(BookEntity bookEntity);
}
