package exception;

public class BookNumberAlreadyExistException extends RuntimeException {

    public BookNumberAlreadyExistException(String message) {
        super(message);
    }

}
