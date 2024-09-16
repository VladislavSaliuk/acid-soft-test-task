package com.example.acidsofttesttask.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Year;

@Data
public class BookDTO {

    @NotEmpty(message = "Book title must not be empty")
    private String title;

    @NotEmpty(message = "Author must not be empty")
    private String author;

    @Max(value = Year.MAX_VALUE, message = "Publication year cannot be greater than the current year")
    private String publicationYear;

    private String genre;

    @NotEmpty(message = "ISBN must not be empty")
    @Size(min = 13, max = 13, message = "ISBN must contain exactly 13 characters")
    private String ISBN;

}