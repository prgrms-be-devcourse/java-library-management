package com.programmers.library_management.exception;

public class CBookAlreadyExistException extends RuntimeException{
    public CBookAlreadyExistException() {
        super("이미 존재하는 책입니다.");
    }
}
