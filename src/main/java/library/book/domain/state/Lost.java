package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Lost implements State {

	private final BookState bookState = LOST;

	@Override
	public BookState getBookState() {
		return bookState;
	}

	@Override
	public void validateIsAbleToRent() {
		throw BookException.of(NOW_LOST);
	}

	@Override
	public void validateIsAbleToLost() {
		throw BookException.of(ALREADY_LOST);
	}
}
