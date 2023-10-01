package library.book.infra.repository.dto;

import java.time.LocalDateTime;

import library.book.domain.Book;
import library.book.domain.State;
import library.book.domain.constants.BookState;
import library.book.domain.state.Cleaning;

public record BookInfo(
	long id,
	String title,
	String authorName,
	int pages,
	String bookState,
	LocalDateTime cleaningEndAt
) {
	public Book toBook() {
		BookState bookState = BookState.valueOf(this.bookState);
		State state = bookState.getState();

		if (state instanceof Cleaning cleaning) {
			cleaning.reset(cleaningEndAt);
		}

		return new Book(
			id,
			title,
			authorName,
			pages,
			state
		);
	}
}
