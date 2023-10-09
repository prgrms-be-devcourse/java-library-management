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
	public void borrow() {
		// success
	}

	@Override
	public void returned() {
		throw new BookException(ErrorCode.BOOK_ALREADY_AVAILABLE);
	}

	@Override
	public void lost() {
		// success
	}

	@Override
	public State organize() {
		return this;
	}
}
