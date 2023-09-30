package org.library.error;

import org.library.entity.Error;

public class InvalidModeError extends RuntimeException{

    public InvalidModeError() {
        super(Error.INVALID_INPUT_MODE.getMessage());
    }
}
