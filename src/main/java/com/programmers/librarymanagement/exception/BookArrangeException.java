package com.programmers.librarymanagement.exception;

public class BookArrangeException extends RuntimeException {

    public BookArrangeException() {
        super("정리 중인 도서입니다.");
    }
}
