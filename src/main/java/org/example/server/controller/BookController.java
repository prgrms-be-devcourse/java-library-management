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
        return bookService.searchByName(bookName) + "\n[System] 검색된 도서 끝\n";
    }

    public String borrow(int bookId) {
        bookService.borrow(bookId);
        return "[System] 도서가 대여 처리 되었습니다.\n";
    }

    public String returnBook(int bookId) {
        bookService.returnBook(bookId);
        return "[System] 도서가 반납 처리 되었습니다.\n";
    }

    public String lost(int bookId) {
        bookService.lost(bookId);
        return "[System] 도서가 분실 처리 되었습니다.\n";
    }
}
