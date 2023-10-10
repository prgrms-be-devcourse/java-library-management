package org.library.exception;

public class InvalidFuncException extends RuntimeException {

    public InvalidFuncException() {
        super(Exception.INVALID_INPUT_FUNC.getMessage());
    }
}
