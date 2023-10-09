package com.programmers.library.entity.state;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class BorrowedState implements State {
	@Override
	public BookStateType getType() {
		return BookStateType.BORROWED;
	}

	@Override
	public void borrow() {
		throw new BookException(ErrorCode.BOOK_ALREADY_BORROWED);
	}

	@Override
	public void returned() {
		// success
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
