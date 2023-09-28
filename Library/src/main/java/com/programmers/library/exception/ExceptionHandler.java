package com.programmers.library.exception;

public class ExceptionHandler extends RuntimeException{
    public ExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
    }

    public static ExceptionHandler err(ErrorCode errorCode){
        return new ExceptionHandler(errorCode);
    }
}
