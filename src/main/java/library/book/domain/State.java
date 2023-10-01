package library.book.domain;

import java.time.LocalDateTime;

import library.book.domain.constants.BookState;

public interface State {

	BookState getBookState();

	default LocalDateTime getCleaningEndAt() {
		return null;
	}

	default void validateIsAbleToRent() {}

	default void validateIsAbleToReturn() {}

	default void validateIsAbleToLost() {}
}
