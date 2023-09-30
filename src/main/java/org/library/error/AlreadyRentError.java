package org.library.error;

import org.library.entity.Error;

public class AlreadyRentError extends RuntimeException{

    public AlreadyRentError() {
        super(Error.ALREADY_RENT.getMessage());
    }
}
