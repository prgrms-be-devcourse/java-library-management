package com.programmers.library_management.exception;

public class CBookNumberNotExistException extends RuntimeException {
    public CBookNumberNotExistException() {
        super("존재하지 않는 도서번호 입니다.");
    }
}
