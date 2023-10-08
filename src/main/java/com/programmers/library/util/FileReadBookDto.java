package com.programmers.library.util;

import java.time.LocalDateTime;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.state.BookStateType;
import com.programmers.library.entity.state.State;

public record FileReadBookDto(
	Long id,
	String title,
	String author,
	Long pages,
	String state,
	LocalDateTime returnedAt
) {
	public Book toEntity() {
		BookStateType bookState = BookStateType.valueOf(this.state);
		State state = bookState.getState();
		return new Book(id, title, author, pages, state, returnedAt);
	}
}
