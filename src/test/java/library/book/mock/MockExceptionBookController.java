package library.book.mock;

import static library.book.exception.ErrorCode.*;

import library.book.exception.BookException;
import library.book.presentation.BookController;

public class MockExceptionBookController extends BookController {

	public MockExceptionBookController() {
		super(null, null);
	}

	@Override
	public void run() {
		throw BookException.of(ONLY_NUMBER);
	}
}
