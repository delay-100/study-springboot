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

    public Long addBook(Book.Create bookForm) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(bookForm.getName());
        bookEntity.setPublisher(bookForm.getPublisher());
        bookEntity.setPrice(bookForm.getPrice());
        bookRepository.save(bookEntity);
        return bookEntity.getId();
    }

    public List<Book.Simple> findCondBooks(Book.Create bookForm) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(bookForm.getName());
        bookEntity.setPublisher(bookForm.getPublisher());
        bookEntity.setPrice(bookForm.getPrice());

        List<Book.Simple> list  = new ArrayList<>();
        for (BookEntity bookEntity2 : bookRepository.findCond(bookEntity)){
            Book.Simple book2 = new Book.Simple();
            book2.setId(bookEntity2.getId());
            book2.setName(bookEntity2.getName());
            book2.setPublisher(bookEntity2.getPublisher());
            book2.setPrice(bookEntity2.getPrice());
            list.add(book2);
        }
        return list;
    }


    public BookEntity getBookById(Long bookId ){
        return bookRepository.findById(bookId).orElseThrow(IllegalArgumentException::new);
    }

    public void updateBookService(Long bookId, Book.Update updateForm){
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(IllegalArgumentException::new);

        bookEntity.setName(updateForm.getName());
        bookEntity.setPublisher(updateForm.getPublisher());
        bookEntity.setPrice(updateForm.getPrice());
        bookRepository.update(bookEntity);
    }

    public void deleteBook(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(IllegalArgumentException::new);

        bookRepository.delete(bookEntity);

    }

}
