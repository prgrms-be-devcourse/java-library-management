package library.book.exception;

public class LibraryException extends RuntimeException {

	private LibraryException(ErrorCode errorCode) {
		super(errorCode.getMessage());
	}

	public static LibraryException of(ErrorCode errorCode) {
		return new LibraryException(errorCode);
	}
}
