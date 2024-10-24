package com.corfiedorg1.Librarydetails.Service;

import com.corfiedorg1.Librarydetails.DTO.BookRequestDto;
import com.corfiedorg1.Librarydetails.DTO.BookResponseDto;
import com.corfiedorg1.Librarydetails.Entity.Book;
import com.corfiedorg1.Librarydetails.Repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookResponseDto addBook(BookRequestDto requestDto) {
        try {
            String validationMessage = validateBookRequest(requestDto);
            if (validationMessage != null) {
                return new BookResponseDto(null, null, null, null, false, validationMessage, false, HttpStatus.BAD_REQUEST);
            }
            Long bookId = requestDto.getBookId();
            if (bookRepository.existsById(bookId)) {
                return new BookResponseDto(null, null, null, null, false, "Book with the same ID already exists!", false, HttpStatus.CONFLICT);
            }

           Book book = requestDto.toEntity();
            book.setIsBorrowed(false);
            bookRepository.save(book);
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    false, "Book added successfully!", true, HttpStatus.CREATED);
        } catch (Exception e) {

            return new BookResponseDto(null, null, null, null, false, "An unexpected error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String validateBookRequest(BookRequestDto requestDto) {
        if (requestDto.getBookId() == null) {
            return "Book ID cannot be null!";
        }
        return null;
    }
    @Override
    public BookResponseDto getBookById(Long id) {
        try {
            if (id == null || id <= 0) {
                return new BookResponseDto(null, null, null, null, false, "Invalid book ID provided.", false, HttpStatus.BAD_REQUEST);
            }

            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    book.isBorrowed(), "Book retrieved successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public BookResponseDto borrowBook(Long id) {
        try {
            if (id == null || id <= 0) {
                return new BookResponseDto(null, null, null, null, false, "Invalid book ID provided.", false, HttpStatus.BAD_REQUEST);
            }
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            if (book.isBorrowed()) {
                return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        true, "Book is already borrowed", true, HttpStatus.CONFLICT);
            }
            book.setIsBorrowed(true);
            book.setBorrowedDate(LocalDate.now());
            bookRepository.save(book);
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    true, "Book borrowed successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public BookResponseDto returnBook(Long id) {
        try {
            Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            if (!book.isBorrowed()) {
                return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        false, "Book is not borrowed", false, HttpStatus.CONFLICT);
            }
            book.setIsBorrowed(false);
            book.setBorrowedDate(null);
            bookRepository.save(book);
            return new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                    false, "Book returned successfully!", true, HttpStatus.OK);
        } catch (Exception e) {
            return new BookResponseDto(null, null, null, null, false, "An error occurred: " + e.getMessage(), false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public List<BookResponseDto> listAvailableBooks() {
        List<Book> availableBooks = bookRepository.findAll().stream().filter(book -> !book.isBorrowed()).collect(Collectors.toList());
        return availableBooks.stream().map(book -> new BookResponseDto(book.getBook_Id(), book.getTitle(), book.getAuthor(), book.getGenre(),
                        false, "Available book: " + book.getTitle(), true, HttpStatus.OK))
                .collect(Collectors.toList());
    }
}
