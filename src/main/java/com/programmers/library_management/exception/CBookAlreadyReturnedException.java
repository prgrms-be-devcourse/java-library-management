package com.programmers.library_management.exception;

public class CBookAlreadyReturnedException extends RuntimeException{
    public CBookAlreadyReturnedException() {
        super("이미 반납된 도서입니다.");
    }
}