package org.library.exception;

import org.library.entity.Exception;

public class InvalidFuncException extends RuntimeException {

    public InvalidFuncException() {
        super(Exception.INVALID_INPUT_FUNC.getMessage());
    }
}
