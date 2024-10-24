

package com.corfiedorg1.Librarydetails.Controller;
import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;
import com.corfiedorg1.Librarydetails.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public BookResponseDto addBook(@RequestBody BookRequestDto requestDto) {
        return bookService.addBook(requestDto);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/borrow/{id}")
    public BookResponseDto borrowBook(@PathVariable Long id) {
        return bookService.borrowBook(id);
    }

    @PutMapping("/return/{id}")
    public BookResponseDto returnBook(@PathVariable Long id) {
        return bookService.returnBook(id);
    }

    @GetMapping("/bookList")
    public List<BookResponseDto> listAvailableBooks() {
        return bookService.listAvailableBooks();
    }
}
