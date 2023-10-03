package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Lost implements State {

	@Override
	public BookState getBookState() {
		return LOST_STATE;
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
