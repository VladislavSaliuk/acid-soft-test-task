package com.example.acidsofttesttask.rest;


import com.example.acidsofttesttask.entity.Book;
import com.example.acidsofttesttask.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookRestController {
    private BookService bookService;
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PutMapping("/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@PathVariable Long id) {
        bookService.removeById(id);
    }

}
