package org.library.exception;

public class InvalidModeException extends RuntimeException {

    public InvalidModeException() {
        super(Exception.INVALID_INPUT_MODE.getMessage());
    }
}
