package com.example.bookmybatis.service;

import com.example.bookmybatis.Entity.BookEntity;
import com.example.bookmybatis.domain.Book;
import com.example.bookmybatis.repository.MybatisBookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final MybatisBookRepository bookRepository;

    public BookService(MybatisBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * 전체 도서 목록 조회
     */
    public List<Book.Simple> findBooks(){
        List<Book.Simple> list = new ArrayList<>();
        for(BookEntity bookEntity: bookRepository.findAll()){
            Book.Simple book = new Book.Simple();
            book.setId(bookEntity.getId());
            book.setName(bookEntity.getName());
            book.setPrice(bookEntity.getPrice());
            book.setPublisher(bookEntity.getPublisher());
            list.add(book);
        }
        return list;
    }
}
