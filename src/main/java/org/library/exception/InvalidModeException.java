package org.library.exception;

import org.library.entity.Exception;

public class InvalidModeException extends RuntimeException {

    public InvalidModeException() {
        super(Exception.INVALID_INPUT_MODE.getMessage());
    }
}
