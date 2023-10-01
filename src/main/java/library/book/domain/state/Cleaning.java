package library.book.domain.state;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.exception.BookException;

public class Cleaning implements State {

	private final BookState bookState;

	private LocalDateTime cleaningEndAt;

	public Cleaning() {
		this.bookState = CLEANING;
		this.cleaningEndAt = LocalDateTime.now().plusMinutes(5);
	}

	@Override
	public LocalDateTime getCleaningEndAt() {
		return cleaningEndAt;
	}

	public void reset(final LocalDateTime cleaningEndAt) {
		this.cleaningEndAt = cleaningEndAt;
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
		if (cleaningEndAt.isAfter(LocalDateTime.now())) {
			throw BookException.of(NOW_CLEANING);
		}
	}
}
