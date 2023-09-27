package com.library.java_library_management.status;

import com.library.java_library_management.dto.BookInfo;

import java.awt.print.Book;
import java.util.function.BiFunction;

public enum BookStatus {

    RENT(((bookInfo, s) -> {
        return "대여중";
    })),
    AVAILABLE(((bookInfo, s) -> {
        bookInfo.setStatus(BookStatus.RENT);
        return "도서가 대여 처리 되었습니다.";
    }) ),
    LOST(((bookInfo, s) -> {
        return "분실";
    })),
    CLEANING(((bookInfo, s) -> {
        return "정리중";
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
