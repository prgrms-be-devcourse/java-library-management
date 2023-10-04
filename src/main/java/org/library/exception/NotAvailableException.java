package org.library.exception;

import org.library.entity.Exception;

public class NotAvailableException extends RuntimeException{

    public NotAvailableException() {
        super(Exception.NOT_AVAILABLE.getMessage());
    }
}
