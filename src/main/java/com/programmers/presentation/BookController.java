package com.programmers.presentation;

import com.programmers.application.BookService;
import com.programmers.presentation.enums.ExitCommand;
import com.programmers.domain.entity.Book;
import com.programmers.domain.dto.RegisterBookReq;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public void registerBook(RegisterBookReq req) {
        bookService.registerBook(req);
    }

    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookService.searchBook(title);
    }

    public void returnBook(Long id) {
        bookService.returnBook(id);
    }
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    public void exitApplication(String optionNum) {
        ExitCommand.promptForExit(optionNum);
    }
}
