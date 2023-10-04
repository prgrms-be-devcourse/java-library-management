package org.library.exception;

import org.library.entity.Exception;

public class AlreadyOrganizingException extends RuntimeException{

    public AlreadyOrganizingException() {
        super(Exception.ALREADY_ORGANIZING.getMessage());
    }
}
