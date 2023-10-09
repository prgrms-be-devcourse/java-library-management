package com.programmers.library.util;

import java.time.LocalDateTime;

import com.programmers.library.entity.Book;
import com.programmers.library.entity.state.BookStateType;
import com.programmers.library.entity.state.OrganizingState;

public record FileWriteBookDto(
	Long id,
	String title,
	String author,
	Long pages,
	BookStateType state,
	LocalDateTime returnedAt
) {
	public static FileWriteBookDto fromEntity(Book book) {
		BookStateType state = book.getStateType();
		LocalDateTime returnedAt = null;
		if (state == BookStateType.ORGANIZING) {
			OrganizingState organizingState = (OrganizingState)book.getState();
			returnedAt = organizingState.getReturnedAt();
		}
		return new FileWriteBookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPages(),
			book.getStateType(), returnedAt);
	}
}
