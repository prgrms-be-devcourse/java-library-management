package com.programmers.librarymanagement.exception;

public class BookLostException extends RuntimeException {

    public BookLostException() {
        super("분실된 도서입니다.");
    }
}
