package com.programmers.library.entity.state;

import java.time.LocalDateTime;

import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class BorrowedState implements State {
	@Override
	public BookStateType getType() {
		return BookStateType.BORROWED;
	}

	@Override
	public State borrow() {
		throw new BookException(ErrorCode.BOOK_ALREADY_BORROWED);
	}

	@Override
	public State returned() {
		return new OrganizingState(LocalDateTime.now());
	}

	@Override
	public State lost() {
		return new LostState();
	}

	@Override
	public State organize() {
		return this;
	}
}
