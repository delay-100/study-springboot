package com.example.bookmybatis.controller;

import com.example.bookmybatis.Entity.BookEntity;
import com.example.bookmybatis.domain.Book;
import com.example.bookmybatis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;
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

    @GetMapping(value="/books/new") // 도서 입력 화면
    public String createForm() {
        return "books/inputBookForm";
    }

    @PostMapping(value="/books/new")
    public String create(Book.Create form) {
        bookService.addBook(form);
        return "redirect:/";
    }

    @GetMapping(value="/books/search")
    public String searchForm() {
        return "books/searchBookForm";
    }

    @PostMapping(value="/books/search")
    public String search(Book.Create form, Model model) {
        List<Book.Simple> books = bookService.findCondBooks(form);
        model.addAttribute("books",books);
        return "books/bookList";
    }

    @GetMapping("/books/{bookId}/update")
    public String getMemberUpdateForm(@PathVariable Long bookId, Model model) {
        BookEntity bookEntity = bookService.getBookById(bookId);
        model.addAttribute("book",bookEntity);
        return "books/updateBookForm";
    }

    @GetMapping("/books/{bookId}")
    public String getBookById(@PathVariable Long bookId,Model model) {
        List<BookEntity> bookSimpleListSingle = new LinkedList<>();
        bookSimpleListSingle.add(bookService.getBookById(bookId));

        model.addAttribute("books",bookSimpleListSingle);
        return "books/bookList";

    }

    @PostMapping("/books/{bookId}")
    public String updateBook(@PathVariable Long bookId, Book.Update updateForm) {
        bookService.updateBookService(bookId, updateForm);
        return "redirect:/books/"+bookId;
    }

    @GetMapping("/books/{bookId}/delete")
    public String getBookDeleteForm(@PathVariable Long bookId, Model model) {
        BookEntity bookEntity = bookService.getBookById(bookId);
        model.addAttribute("book", bookEntity);
        return "books/deleteBookForm";
    }

    @PostMapping("/books/{bookId}/delete")
    public String getBookDelete(@PathVariable Long bookId, Model model) {
        model.addAttribute("id",bookId);
        bookService.deleteBook(bookId);
        return "redirect:/books";

    }
}
