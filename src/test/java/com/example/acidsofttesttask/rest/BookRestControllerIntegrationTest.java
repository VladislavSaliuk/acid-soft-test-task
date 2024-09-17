package com.example.acidsofttesttask.rest;


import com.example.acidsofttesttask.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_books.sql"})
public class BookRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    Book book;
    @BeforeEach
    void setUp() {
        book = Book.builder()
                .bookId(11L)
                .title("Test title")
                .author("Test author")
                .publicationYear(2024)
                .genre("Test genre")
                .ISBN("1234567890123")
                .build();
    }
    @Test
    void createBook_shouldReturnCreatedStatus() throws Exception {

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.bookId").value(11L));

    }

    @Test
    void updateBook_shouldReturnNoContentStatus() throws Exception {

        String updatedTitle = "Test title 1";

        book.setBookId(1L);
        book.setTitle(updatedTitle);

        mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/" + book.getBookId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.bookId").value(book.getBookId()))
                .andExpect(jsonPath("$.title").value(updatedTitle))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$.genre").value(book.getGenre()))
                .andExpect(jsonPath("$.isbn").value(book.getISBN()));

    }
    @Test
    void getBooks_shouldReturnOkStatus() throws Exception {

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));

    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void getBookById_shouldReturnOkStatus(long bookId) throws Exception {

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$.bookId").value(bookId));

    }


    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L})
    void removeBookById_shouldReturnNoContentStatus(long bookId) throws Exception {

        mockMvc.perform(delete("/books/" + bookId))
                .andExpect(status().isNoContent());

    }



}
