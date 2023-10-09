package com.programmers.app.exception;

import com.programmers.app.exception.messages.ExceptionMessages;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(ExceptionMessages exceptionMessages) {
        super(exceptionMessages.getExceptionMessage());
    }
}
