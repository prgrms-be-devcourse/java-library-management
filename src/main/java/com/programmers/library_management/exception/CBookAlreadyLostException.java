package com.programmers.library_management.exception;

public class CBookAlreadyLostException extends RuntimeException {
    public CBookAlreadyLostException() {
        super("이미 분실 처리된 도서입니다.");
    }
}
