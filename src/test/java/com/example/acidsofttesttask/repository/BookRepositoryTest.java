package com.example.acidsofttesttask.repository;


import com.example.acidsofttesttask.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_books.sql"})
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void save_shouldSaveBookToDatabase() {
        Book book = new Book();
        bookRepository.save(book);
        assertEquals(bookRepository.count(), 11);
    }
    @Test
    void findAll_shouldReturnBookList() {
        List<Book> bookList = bookRepository.findAll();
        assertFalse(bookList.isEmpty());
        assertEquals(bookList.size(), 10);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void deleteById_shouldDeleteBookFromDatabase_whenInputContainsExistingBookId(long bookId) {
        bookRepository.deleteById(bookId);
        assertEquals(bookRepository.count(), 9);
    }

    @ParameterizedTest
    @ValueSource(longs = {11L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L, 100L})
    void deleteById_shouldNotDeleteBookFromDatabase_whenInputContainsNotExistingBookId(long bookId) {
        bookRepository.deleteById(bookId);
        assertEquals(bookRepository.count(), 10);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void findById_shouldReturnBook_whenInputContainsExistingBookId(long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        assertTrue(optionalBook.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {11L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L, 100L})
    void findById_shouldReturnNull_whenInputContainsNotExistingBookId(long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        assertTrue(optionalBook.isEmpty());
    }

}
