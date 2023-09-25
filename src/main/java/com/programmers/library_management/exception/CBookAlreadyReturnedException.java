package com.programmers.library_management.exception;

public class CBookAlreadyReturnedException extends RuntimeException{
    public CBookAlreadyReturnedException() {
        super("원래 대여가 가능한 도서입니다.");
    }
}
