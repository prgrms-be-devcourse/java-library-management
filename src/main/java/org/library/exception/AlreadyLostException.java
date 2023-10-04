package org.library.exception;

import org.library.entity.Exception;

public class AlreadyLostException extends RuntimeException {

    public AlreadyLostException() {
        super(Exception.ALREADY_REPORT_LOST.getMessage());
    }
}
