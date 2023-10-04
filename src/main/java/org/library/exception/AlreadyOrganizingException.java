package org.library.exception;

public class AlreadyOrganizingException extends RuntimeException {

    public AlreadyOrganizingException() {
        super(Exception.ALREADY_ORGANIZING.getMessage());
    }
}
