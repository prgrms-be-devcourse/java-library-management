package com.programmers.app.exception;

import com.programmers.app.exception.messages.ExceptionMessages;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super(ExceptionMessages.INCORRECT_INPUT_NUMBER.getExceptionMessage());
    }
}
