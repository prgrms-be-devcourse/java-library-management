package library.book.infra.repository.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(
		shape = STRING,
		pattern = "yyyy-MM-dd a HH:mm")
	LocalDateTime cleaningEndTime
) {
	public Book toBook() {
		BookState bookState = BookState.valueOf(this.bookState);

		State state = bookState.getStateSupplier();
		if (state instanceof Cleaning cleaning) {
			cleaning.resetState(cleaningEndTime);
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
