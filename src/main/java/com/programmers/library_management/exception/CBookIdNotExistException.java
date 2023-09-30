package com.programmers.library_management.exception;

public class CBookIdNotExistException extends RuntimeException {
    public CBookIdNotExistException() {
        super("존재하지 않는 도서번호 입니다.");
    }
}
