package com.example.acidsofttesttask.service;


import com.example.acidsofttesttask.entity.Book;
import com.example.acidsofttesttask.exception.BookException;
import com.example.acidsofttesttask.exception.BookNotFoundException;
import com.example.acidsofttesttask.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    BookService bookService;
    @Mock
    BookRepository bookRepository;
    Book book;
    @BeforeEach
    void setUp() {
        book = Book.builder()
                .bookId(1L)
                .title("Test title")
                .author("Test author")
                .publicationYear(2024)
                .genre("Test genre")
                .ISBN("1234567890123")
                .build();
    }
    @Test
    void createBook_shouldReturnBook_whenInputContainsBook() {

        when(bookRepository.existsByISBN(book.getISBN()))
                .thenReturn(false);

        when(bookRepository.save(book))
                .thenReturn(book);

        Book actualBook = bookService.createBook(book);

        assertEquals(book, actualBook);

        verify(bookRepository).existsByISBN(book.getISBN());
        verify(bookRepository).save(book);

    }

    @Test
    void createBook_shouldThrowException_whenInputContainsBookWithExistingISBN() {

        when(bookRepository.existsByISBN(book.getISBN()))
                .thenReturn(true);

        BookException exception = assertThrows(BookException.class, () -> bookService.createBook(book));

        assertEquals("Book with " + book.getISBN() + " ISBN already exists!", exception.getMessage());

        verify(bookRepository).existsByISBN(book.getISBN());
        verify(bookRepository, never()).save(book);

    }

    @Test
    void getAll_shouldReturnBookList() {

        List<Book> expectedBookList = List.of(book);

        when(bookRepository.findAll())
                .thenReturn(expectedBookList);

        List<Book> actualBookList = bookService.getAll();

        assertFalse(actualBookList.isEmpty());
        assertEquals(expectedBookList, actualBookList);

        verify(bookRepository).findAll();

    }

    @Test
    void getById_shouldReturnBook_whenInputContainsExistingBookId() {

        long bookId = 1L;

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.ofNullable(book));

        Book actualBook = bookService.getById(bookId);

        assertNotNull(actualBook);
        assertEquals(book, actualBook);

        verify(bookRepository).findById(bookId);

    }

    @Test
    void getById_shouldThrowException_whenInputContainsNotExistingBookId() {

        long bookId = 2L;

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.getById(bookId));

        assertEquals("Book with Id " + bookId + " not found!", exception.getMessage());

        verify(bookRepository).findById(bookId);

    }

    @Test
    void updateBook_shouldUpdateBook_whenInputContainsBook() {

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.ofNullable(book));

        when(bookRepository.existsByISBN(book.getISBN()))
                .thenReturn(false);

        bookService.updateBook(book);

        verify(bookRepository).findById(book.getBookId());
        verify(bookRepository).existsByISBN(book.getISBN());

    }

    @Test
    void updateBook_shouldThrowException_whenInputContainsBookWithNotExistingId() {

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(book));

        assertEquals("Book with Id " + book.getBookId() + " not found!", exception.getMessage());

        verify(bookRepository).findById(book.getBookId());
        verify(bookRepository, never()).existsByISBN(book.getISBN());

    }

    @Test
    void updateBook_shouldThrowException_whenInputContainsBookWithExistingISBN() {

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.ofNullable(book));

        when(bookRepository.existsByISBN(book.getISBN()))
                .thenReturn(true);

        BookException exception = assertThrows(BookException.class, () -> bookService.updateBook(book));

        assertEquals("Book with " + book.getISBN() + " ISBN already exists!", exception.getMessage());

        verify(bookRepository).findById(book.getBookId());
        verify(bookRepository).existsByISBN(book.getISBN());

    }

    @Test
    void removeById_shouldDeleteBookFromDatabase_whenInputContainsExistingBookId() {

        long bookId = 1L;

        when(bookRepository.existsByBookId(bookId))
                .thenReturn(true);

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.removeById(bookId);

        verify(bookRepository).existsByBookId(bookId);
        verify(bookRepository).deleteById(bookId);

    }

    @Test
    void removeById_shouldThrowException_whenInputContainsBookWithNotExistingId() {

        long bookId = 11L;

        when(bookRepository.existsByBookId(bookId))
                .thenReturn(false);

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.removeById(bookId));

        assertEquals("Book with Id " + bookId + " not found!", exception.getMessage());

        verify(bookRepository).existsByBookId(bookId);
        verify(bookRepository, never()).deleteById(bookId);

    }


}
