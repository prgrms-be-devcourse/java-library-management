package com.libraryManagement.exception;

public class BookException extends RuntimeException{

    public BookException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
