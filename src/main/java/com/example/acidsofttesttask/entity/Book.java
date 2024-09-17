package com.example.acidsofttesttask.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name = "book_id_generator", initialValue = 1, allocationSize = 1, sequenceName = "book_id_seq")
    private long bookId;

    private String title;

    private String author;

    @Column(name = "publication_year")
    private int publicationYear;

    private String genre;

    @Column(unique = true)
    private String ISBN;

}
