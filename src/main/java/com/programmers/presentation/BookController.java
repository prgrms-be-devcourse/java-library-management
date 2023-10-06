package com.programmers.presentation;

import com.programmers.application.BookService;
import com.programmers.domain.dto.BookResponse;
import com.programmers.domain.dto.RegisterBookReq;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public void registerBook(RegisterBookReq req) {
        bookService.registerBook(req);
    }

    public List<BookResponse> getAllBooks() {
        return bookService.findAllBooks();
    }

    public List<BookResponse> searchBooksByTitle(String title) {
        return bookService.searchBook(title);
    }

    public void rentBook(Long id) {
        bookService.rentBook(id);
    }

    public void returnBook(Long id) {
        bookService.returnBook(id);
    }

    public void reportLostBook(Long id) {
        bookService.reportLostBook(id);
    }

    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

}
