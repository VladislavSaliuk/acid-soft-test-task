package com.example.acidsofttesttask.rest;


import com.example.acidsofttesttask.entity.Book;
import com.example.acidsofttesttask.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookRestController {
    private BookService bookService;
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody @Valid Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public Page<Book> getBooks(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        return bookService.getAll(pageable);
    }

    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PutMapping("/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@RequestBody @Valid Book book) {
        bookService.updateBook(book);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@PathVariable Long id) {
        bookService.removeById(id);
    }

}
