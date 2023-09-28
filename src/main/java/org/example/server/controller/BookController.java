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

    public String readAllBook() {
        return bookService.readAll() + "[System] 도서 목록 끝\n";
    }

    public String seachByName(String bookName) {
        return bookService.searchByName(bookName) + "[System] 검색된 도서 끝\n";
    }
}
