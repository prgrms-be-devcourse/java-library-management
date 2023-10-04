package org.library.exception;

import org.library.entity.Exception;

public class NotReturnsException extends RuntimeException {

    public NotReturnsException() {
        super(Exception.NOT_RETURNS.getMessage());
    }
}
