package com.programmers.library.entity.state;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class AvailableState implements State {

	@Override
	public BookStateType getType() {
		return BookStateType.AVAILABLE;
	}

	@Override
	public State borrow() {
		return new BorrowedState();
	}

	@Override
	public State returned() {
		throw new BookException(ErrorCode.BOOK_ALREADY_AVAILABLE);
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
