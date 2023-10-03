package library.book.infra.repository.dto;

import library.book.domain.Book;
import library.book.domain.State;
import library.book.domain.constants.BookState;

public record BookInfo(
	long id,
	String title,
	String authorName,
	int pages,
	String bookState
) {
	public Book toBook() {
		BookState bookState = BookState.valueOf(this.bookState);
		State state = bookState.getState();

		return new Book(
			id,
			title,
			authorName,
			pages,
			state
		);
	}
}
