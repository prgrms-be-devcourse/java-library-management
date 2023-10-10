package org.library.exception;

public class NotExistException extends RuntimeException {

    public NotExistException() {
        super(Exception.NOT_EXIST.getMessage());
    }
}
