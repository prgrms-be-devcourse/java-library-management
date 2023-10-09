package dev.course.exception;

public class FileIOFailureException extends RuntimeException {

    public FileIOFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
