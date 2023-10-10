package org.library.exception;

public class NotReturnsException extends RuntimeException {

    public NotReturnsException() {
        super(Exception.NOT_RETURNS.getMessage());
    }
}
