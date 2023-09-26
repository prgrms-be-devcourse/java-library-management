package library.book.stub;

import static library.book.exception.ErrorCode.*;

import library.book.exception.BookException;
import library.book.presentation.BookController;

public class StubExceptionBookController extends BookController {

	public StubExceptionBookController() {
		super(null, null);
	}

	@Override
	public void run() {
		throw BookException.of(ONLY_NUMBER);
	}
}
