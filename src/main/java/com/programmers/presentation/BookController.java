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

