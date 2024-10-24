package com.corfiedorg1.Librarydetails.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long Book_id;
    private String title;
    private String author;
    private String genre;
    private boolean isBorrowed;
    private String message;
    private boolean success;
    private HttpStatus httpStatus;
}
