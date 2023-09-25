package com.library.java_library_management.status;

import com.library.java_library_management.dto.BookInfo;

import java.awt.print.Book;
import java.util.function.BiFunction;

public enum BookStatus {

    RENT(((bookInfo, s) -> {
        return "이미 대여중인 도서입니다.";
    })),
    AVAILABLE(((bookInfo, s) -> {
        bookInfo.setStatus(BookStatus.RENT);
        return "";
    }) ),
    LOST(((bookInfo, s) -> {
        return "분실된 도서입니다.";
    })),
    CLEANING(((bookInfo, s) -> {
        return "정리중인 도서입니다.";
    }));

    private final BiFunction<BookInfo, String, String> expression;

    BookStatus(BiFunction<BookInfo, String, String> expression) {
        this.expression = expression;
    }

    public String rentBook(BookInfo bookInfo){
        String message = "";
        return this.expression.apply(bookInfo, message);
    }
}
