package com.programmers.library_management.exception;

public class CBookInOrganizeException extends RuntimeException {
    public CBookInOrganizeException() {
        super("정리중인 도서입니다.");
    }
}
