package com.example.acidsofttesttask.service;

import com.example.acidsofttesttask.entity.Book;
import com.example.acidsofttesttask.exception.BookNotFoundException;
import com.example.acidsofttesttask.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with Id " + bookId + " not found."));
    }

    @Transactional
    public void updateBook(Book book) {

        Book updatedBook = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book with Id " + book.getBookId() + " not found."));

        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setPublicationYear(book.getPublicationYear());
        updatedBook.setGenre(book.getGenre());
        updatedBook.setISBN(book.getISBN());

    }

    public void removeById(long bookId) {
        bookRepository.deleteById(bookId);
    }

}
