package library.book.domain.state;

import static library.book.domain.constants.BookState.*;

import java.time.LocalDateTime;

import library.book.domain.State;
import library.book.domain.constants.BookState;

public class Cleaning implements State {

	private final BookState bookState = CLEANING;
	private LocalDateTime cleaningEndAt;

	@Override
	public BookState getBookState() {
		return bookState;
	}
}
