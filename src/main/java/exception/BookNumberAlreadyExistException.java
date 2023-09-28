package exception;

public class BookNumberAlreadyExistException extends RuntimeException {
    public BookNumberAlreadyExistException() {
        super();
    }

    public BookNumberAlreadyExistException(String message) {
        super(message);
    }

    public BookNumberAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNumberAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected BookNumberAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
