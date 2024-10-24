package com.corfiedorg1.Librarydetails.DTO;

import com.corfiedorg1.Librarydetails.Entity.Book;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestDto {
    private Long bookId; // Match the Book entity field
    private String title;
    private String author;
    private String genre;
    private boolean isBorrowed;
    private LocalDate borrowedDate;

    public Book toEntity() {
        Book book = new Book();
        book.setBook_Id(this.bookId);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setGenre(this.genre);
        book.setisBorrowed(this.isBorrowed);
        book.setBorrowedDate(this.borrowedDate);
        book.setActive(true);  // Assuming the book is active by default
        return book;
    }

}
