package com.programmers.librarymanagement.exception;

public class BookAlreadyRentException extends RuntimeException {

    public BookAlreadyRentException() {
        super("이미 대여중인 도서입니다.");
    }
}
