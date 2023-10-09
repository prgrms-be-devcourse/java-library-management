package com.programmers.librarymanagement.exception;

public class BookAlreadyLostException extends RuntimeException {

    public BookAlreadyLostException() {
        super("이미 분실 처리된 도서입니다.");
    }
}
