package com.example.acidsofttesttask.repository;

import com.example.acidsofttesttask.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByBookId(long bookId);
    boolean existsByISBN(String ISBN);

}
