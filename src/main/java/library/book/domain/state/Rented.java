package library.book.domain.state;

import static library.book.domain.constants.BookState.*;

import library.book.domain.State;
import library.book.domain.constants.BookState;

public class Rented implements State {

	private final BookState bookState = RENTED;

	@Override
	public BookState getBookState() {
		return bookState;
	}
}
