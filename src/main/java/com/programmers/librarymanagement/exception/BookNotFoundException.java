package com.programmers.librarymanagement.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("해당하는 도서가 존재하지 않습니다.");
    }
}
