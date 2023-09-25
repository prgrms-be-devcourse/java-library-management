package library.book.exception;

public class BookException extends RuntimeException {

	private BookException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}

	public static BookException of(ErrorCode errorCode) {
		return new BookException(errorCode);
	}
}
