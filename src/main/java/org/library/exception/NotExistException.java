package org.library.exception;

import org.library.entity.Exception;

public class NotExistException extends RuntimeException {

    public NotExistException() {
        super(Exception.NOT_EXIST.getMessage());
    }
}
