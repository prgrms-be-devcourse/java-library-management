package com.libraryManagement.exception;

public class myException extends RuntimeException{

    public myException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
