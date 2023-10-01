package dev.course.exception;

public class ConsoleIOFailureException extends RuntimeException {

    public ConsoleIOFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
