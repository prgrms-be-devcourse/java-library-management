package com.programmers.library_management.exception;

public class CBookAlreadyRantedException extends RuntimeException{
    public CBookAlreadyRantedException() {
        super("이미 대여중인 도서입니다.");
    }
}
