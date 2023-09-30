package org.library.error;

import org.library.entity.Error;

public class InvalidFuncError extends RuntimeException{

    public InvalidFuncError() {
        super(Error.INVALID_INPUT_FUNC.getMessage());
    }
}
