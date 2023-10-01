package library.book.domain;

import library.book.domain.constants.BookState;

public interface State {

	BookState getBookState();

	default void validateIsAbleToRent() {}
}
