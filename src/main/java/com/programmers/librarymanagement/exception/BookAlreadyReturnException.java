package com.programmers.librarymanagement.exception;

public class BookAlreadyReturnException extends RuntimeException {

    public BookAlreadyReturnException() {
        super("원래 대여가 가능한 도서입니다.");
    }
}
