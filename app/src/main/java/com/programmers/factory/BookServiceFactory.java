package com.programmers.factory;

import com.programmers.service.BookService;

import java.util.Optional;

public class BookServiceFactory {
    private static BookService bookService;

    public static void setMode(int mode) {
        if (bookService == null) {
            bookService = new BookService(BookRepositoryFactory.getBookRepository(mode));
        } else {
            throw new IllegalStateException("BookService 인스턴스가 이미 존재합니다.");
        }

    }

    public static BookService getBookService() {
        return Optional.ofNullable(bookService)
                .orElseThrow(() -> new IllegalStateException("setMode()를 먼저 실행해주세요"));
    }
}
