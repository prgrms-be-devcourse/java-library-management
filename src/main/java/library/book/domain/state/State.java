package library.book.domain.state;

import library.book.domain.constants.BookState;

public interface State {

	BookState getBookState();

	default void validateIsAbleToRent() {}

	default void validateIsAbleToReturn() {}

	default void validateIsAbleToLost() {}
}
