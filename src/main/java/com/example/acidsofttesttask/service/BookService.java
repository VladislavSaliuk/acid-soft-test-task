package com.example.acidsofttesttask.service;

import com.example.acidsofttesttask.entity.Book;
import com.example.acidsofttesttask.exception.BookException;
import com.example.acidsofttesttask.exception.BookNotFoundException;
import com.example.acidsofttesttask.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {

        if(bookRepository.existsByISBN(book.getISBN())) {
            throw new BookException("Book with " + book.getISBN() + " ISBN already exists!");
        }

        return bookRepository.save(book);
    }

    public Page<Book> getAll(Pageable pageable) {

        if (pageable.getOffset() < 0) {
            throw new IllegalArgumentException("Offset must be a non-negative integer.");
        }

        if (pageable.getPageSize() <= 0) {
            throw new IllegalArgumentException("Page size must be a positive integer.");
        }

        return bookRepository.findAll(pageable);
    }

    public List<Book> searchBooks(String title, String author, String genre) {

        Specification<Book> specification = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (author != null && !author.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }
        if (genre != null && !genre.isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("genre")), "%" + genre.toLowerCase() + "%"));
        }

        return bookRepository.findAll(specification);
    }

    public Book getById(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with Id " + bookId + " not found!"));
    }

    @Transactional
    public void updateBook(Book book) {

        Book updatedBook = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book with Id " + book.getBookId() + " not found!"));

        if(bookRepository.existsByISBN(book.getISBN())) {
            throw new BookException("Book with " + book.getISBN() + " ISBN already exists!");
        } else {
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setPublicationYear(book.getPublicationYear());
            updatedBook.setGenre(book.getGenre());
            updatedBook.setISBN(book.getISBN());
        }

    }

    public void removeById(long bookId) {
        if(bookRepository.existsByBookId(bookId)) {
            bookRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException("Book with Id " + bookId + " not found!");
        }
    }

}
