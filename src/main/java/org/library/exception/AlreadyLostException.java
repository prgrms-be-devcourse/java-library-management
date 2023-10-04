package org.library.exception;

public class AlreadyLostException extends RuntimeException {

    public AlreadyLostException() {
        super(Exception.ALREADY_REPORT_LOST.getMessage());
    }
}
