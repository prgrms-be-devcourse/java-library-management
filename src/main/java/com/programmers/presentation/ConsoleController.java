package com.programmers.presentation;

import com.programmers.application.BookService;
import com.programmers.mediator.dto.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsoleController implements Controller<String> {
    private final BookService bookService;

    @Override
    public Response<String> registerBook() {
        return null;
    }

    @Override
    public Response<String> getAllBooks() {
        return null;
    }

    @Override
    public Response<String> searchBooksByTitle() {
        return null;
    }

    @Override
    public Response<String> rentBook() {
        return null;
    }

    @Override
    public Response<String> returnBook() {
        return null;
    }

    @Override
    public Response<String> reportLostBook() {
        return null;
    }

    @Override
    public Response<String> deleteBook() {
        return null;
    }
}
