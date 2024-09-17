package com.example.acidsofttesttask.repository;


import com.example.acidsofttesttask.entity.Book;
import org.junit.jupiter.api.BeforeAll;
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
    static Book book;
    @BeforeAll
    static void init() {
        book = Book.builder()
                .title("Test title")
                .author("Test author")
                .publicationYear(2024)
                .genre("Test genre")
                .ISBN("1234567890123")
                .build();
    }
    @Test
    void save_shouldSaveBookToDatabase() {
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

    @Test
    void deleteById_shouldNotDeleteBookFromDatabase_whenInputContainsNotExistingBookId() {
        long bookId = 100L;
        bookRepository.deleteById(bookId);
        assertEquals(bookRepository.count(), 10);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void findById_shouldReturnBook_whenInputContainsExistingBookId(long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        assertTrue(optionalBook.isPresent());
    }

    @Test
    void findById_shouldReturnNull_whenInputContainsNotExistingBookId() {
        long bookId = 100L;
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        assertTrue(optionalBook.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void existsByBookId_shouldReturnTrue_whenInputContainsExistingBookId(long bookId) {
        boolean isBookExists = bookRepository.existsByBookId(bookId);
        assertTrue(isBookExists);
    }

    @Test
    void existsByBookId_shouldReturnFalse_whenInputContainsNotExistingBookId() {
        long bookId = 100L;
        boolean isBookExists = bookRepository.existsByBookId(bookId);
        assertFalse(isBookExists);
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "9783161484100",
            "9783161484101",
            "9783161484102",
            "9783161484103",
            "9783161484104",
            "9783161484105",
            "9783161484106",
            "9783161484107",
            "9783161484108",
            "9783161484109"
    })
    void existsByISBN_shouldReturnTrue_whenInputContainsExistingISBN(String ISBN) {
        boolean isBookExists = bookRepository.existsByISBN(ISBN);
        assertTrue(isBookExists);
    }
    @Test
    void existsByISBN_shouldReturnFalse_whenInputContainsNotExistingISBN() {
        String ISBN = "Test ISBN";
        boolean isBookExists = bookRepository.existsByISBN(ISBN);
        assertFalse(isBookExists);
    }

}