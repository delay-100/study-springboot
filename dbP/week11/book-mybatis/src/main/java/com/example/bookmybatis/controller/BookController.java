package com.example.bookmybatis.controller;

import com.example.bookmybatis.domain.Book;
import com.example.bookmybatis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){this.bookService=bookService;}

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping(value="/books")
    public String list(Model model){
        System.out.println("*** books mapping ***");
        List<Book.Simple> books = bookService.findBooks();
        model.addAttribute("books", books);
        return "books/bookList";
    }

}
