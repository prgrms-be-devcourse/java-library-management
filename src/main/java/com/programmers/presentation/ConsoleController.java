package com.programmers.presentation;

import com.programmers.application.BookService;
import com.programmers.mediator.dto.ConsoleResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleController implements Controller<String> {
    private final BookService bookService;

    @Override
    public ConsoleResponse registerBook() {
        return null;
    }

    @Override
    public ConsoleResponse getAllBooks() {
        return null;
    }

    @Override
    public ConsoleResponse searchBooksByTitle() {
        return null;
    }

    @Override
    public ConsoleResponse rentBook() {
        return null;
    }

    @Override
    public ConsoleResponse returnBook() {
        return null;
    }

    @Override
    public ConsoleResponse reportLostBook() {
        return null;
    }

    @Override
    public ConsoleResponse deleteBook() {
        return null;
    }

    @Override
    public ConsoleResponse exitApplication() {
        return ConsoleResponse.of(bookService.exitApp());
    }
}
