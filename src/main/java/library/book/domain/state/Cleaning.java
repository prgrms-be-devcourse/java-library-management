package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Cleaning implements State {

	private final BookState bookState = CLEANING;
	private LocalDateTime cleaningEndAt;

	@Override
	public BookState getBookState() {
		return bookState;
	}

	@Override
	public void validateIsAbleToRent() {
		throw BookException.of(NOW_CLEANING);
	}
}
