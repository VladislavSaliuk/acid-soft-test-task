package com.example.acidsofttesttask.repository;

import com.example.acidsofttesttask.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}