package library.exception;

public class BookException extends RuntimeException {

    public BookException(BookErrorMessage bookErrorMessage) {
        super(bookErrorMessage.getMessage());
    }
}
