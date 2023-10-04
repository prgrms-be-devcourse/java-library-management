package org.library.exception;

import org.library.entity.Exception;

public class AlreadyRentException extends RuntimeException {

    public AlreadyRentException() {
        super(Exception.ALREADY_RENT.getMessage());
    }
}
