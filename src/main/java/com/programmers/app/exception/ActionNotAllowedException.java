package com.programmers.app.exception;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String error) {
        super(error);
    }
}
