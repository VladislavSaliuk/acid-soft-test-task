package com.example.acidsofttesttask.service;


import com.example.acidsofttesttask.entity.Book;
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
                .ISBN("1234567890123111111")
                .build();
    }
    @Test
    void createBook_shouldReturnBook_whenInputContainsBook() {

        when(bookRepository.save(book))
                .thenReturn(book);

        Book actualBook = bookService.createBook(book);

        assertNotNull(actualBook);
        assertEquals(book, actualBook);

        verify(bookRepository).save(book);

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

        assertEquals("Book with Id " + bookId + " not found.", exception.getMessage());

        verify(bookRepository).findById(bookId);

    }

    @Test
    void updateBook_shouldUpdateBook_whenInputContainsBook() {

        Book updatedBook = Book.builder()
                .bookId(1L)
                .title("Test title 1")
                .author("Test author 1")
                .publicationYear(2022)
                .genre("Test genre 1")
                .ISBN("111111111111")
                .build();

        when(bookRepository.findById(book.getBookId()))
                .thenReturn(Optional.ofNullable(book));

        bookService.updateBook(updatedBook);

        verify(bookRepository).findById(book.getBookId());

    }

    @Test
    void removeById_shouldDeleteBookFromDatabase_whenInputContainsExistingBookId() {

        long bookId = 1L;

        doNothing().when(bookRepository).deleteById(bookId);

        bookService.removeById(bookId);

        verify(bookRepository).deleteById(bookId);

    }


}
