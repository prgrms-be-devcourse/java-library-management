package library.book.domain.state;

import static library.book.domain.constants.BookState.*;

import library.book.domain.State;
import library.book.domain.constants.BookState;

public class Lost implements State {

	private final BookState bookState = LOST;

	@Override
	public BookState getBookState() {
		return bookState;
	}
}
