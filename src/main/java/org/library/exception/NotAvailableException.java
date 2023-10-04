package org.library.exception;

public class NotAvailableException extends RuntimeException {

    public NotAvailableException() {
        super(Exception.NOT_AVAILABLE.getMessage());
    }
}
