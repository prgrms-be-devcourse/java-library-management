package com.programmers.exception;

public class LibraryException extends RuntimeException{

    private final ErrorCode errorCode;

    public LibraryException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
