package org.library.exception;

public class AlreadyRentException extends RuntimeException {

    public AlreadyRentException() {
        super(Exception.ALREADY_RENT.getMessage());
    }
}
