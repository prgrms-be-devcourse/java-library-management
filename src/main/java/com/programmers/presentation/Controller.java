package com.programmers.presentation;

import com.programmers.mediator.dto.Response;

public interface Controller<R> {
    Response<R> registerBook();

    Response<R> getAllBooks();

    Response<R> searchBooksByTitle();

    Response<R> rentBook();

    Response<R> returnBook();

    Response<R> reportLostBook();

    Response<R> deleteBook();

    Response<R> exitApplication();
}
