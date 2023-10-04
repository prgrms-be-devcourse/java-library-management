package dev.course.exception;

public class MaxRetryFailureException extends RuntimeException {

    public MaxRetryFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
