package com.example.bookmybatis.config;

import com.example.bookmybatis.repository.MybatisBookRepository;
import com.example.bookmybatis.service.BookService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.example.bookmybatis.repository")
public class SpringConfig {
    private final MybatisBookRepository bookRepository;

    public SpringConfig(MybatisBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Bean
    public BookService bookService(){
        return new BookService(bookRepository);
    }
}
