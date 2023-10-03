package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class AvailableRent implements State {

	@Override
	public BookState getBookState() {
		return AVAILABLE_RENT_STATE;
	}

	@Override
	public void validateIsAbleToReturn() {
		throw BookException.of(ALREADY_AVAILABLE_RENT);
	}
}
