package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Cleaning implements State {

	public static final long CLEANING_END_AT = 5;

	@Override
	public BookState getBookState() {
		return CLEANING_STATE;
	}

	@Override
	public void validateIsAbleToRent() {
		throw BookException.of(NOW_CLEANING);
	}

	@Override
	public void validateIsAbleToReturn() {
		throw BookException.of(NOW_CLEANING);
	}
}
