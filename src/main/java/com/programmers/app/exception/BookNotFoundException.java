package com.programmers.app.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("[System] 보관된 책이 없습니다");
    }
}
