package org.example.server.controller;

import org.example.client.connect.RequestData;
import org.example.server.entity.RequestBookDto;
import org.example.server.service.BookService;

public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public String registerBook(RequestBookDto requestBookDto) {
        bookService.register(requestBookDto);
        return "[System] 도서 등록이 완료되었습니다.\n";
    }
}
