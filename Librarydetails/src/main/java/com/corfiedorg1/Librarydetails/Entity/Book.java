package com.corfiedorg1.Librarydetails.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Book_Id;
    private String title;
    private String author;
    private String genre;
    @Column(name = "is_borrowed")
    private boolean isBorrowed;
    private LocalDate borrowedDate;
    @Column(name = "active")
    private boolean active;

    public boolean isBorrowed() {
        return isBorrowed;
    }
    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
    public void setisBorrowed(boolean b) {
    }
}
