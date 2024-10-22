package com.example.acidsofttesttask.entity;


import com.example.acidsofttesttask.validation.ValidPublicationYear;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Book title must not be empty")
    private String title;

    @NotEmpty(message = "Author must not be empty")
    private String author;

    @Column(name = "publication_year")
    @ValidPublicationYear
    private int publicationYear;

    private String genre;

    @Column(unique = true)
    @NotEmpty(message = "ISBN must not be empty")
    @Size(min = 13, max = 13, message = "ISBN must contain exactly 13 characters")
    private String ISBN;

}
