package com.programmers.app.exception;

import com.programmers.app.exception.messages.ExceptionMessages;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(ExceptionMessages exceptionMessages) {
        super(exceptionMessages.getExceptionMessage());
    }
}
