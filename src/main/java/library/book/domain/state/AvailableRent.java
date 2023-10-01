package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class AvailableRent implements State {

	private final BookState bookState = AVAILABLE_RENT;

	@Override
	public BookState getBookState() {
		return bookState;
	}

	@Override
	public void validateIsAbleToRent() {}
}
