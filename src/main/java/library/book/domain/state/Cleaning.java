package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Cleaning implements State {

	private final BookState bookState;
	private final LocalDateTime cleaningEndAt;

	public Cleaning() {
		this.bookState = CLEANING;
		this.cleaningEndAt = LocalDateTime.now().plusMinutes(5);
	}

	@Override
	public BookState getBookState() {
		validateCleaningEndAtIsNotNull();

		if (cleaningEndAt.isBefore(LocalDateTime.now())) {
			return AVAILABLE_RENT;
		}
		return bookState;
	}

	private void validateCleaningEndAtIsNotNull() {
		if (cleaningEndAt == null) {
			throw BookException.of(INVALID_CLEANING_END_TIME);
		}
	}

	@Override
	public void validateIsAbleToRent() {
		throw BookException.of(NOW_CLEANING);
	}
}
