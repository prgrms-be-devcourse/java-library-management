package com.programmers.library.entity.state;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class LostState implements State {
	@Override
	public BookStateType getType() {
		return BookStateType.LOST;
	}

	@Override
	public void borrow() {
		throw new BookException(ErrorCode.BOOK_LOST);
	}

	@Override
	public void returned() {
		// success
	}

	@Override
	public void lost() {
		throw new BookException(ErrorCode.BOOK_LOST);
	}

	@Override
	public State organize() {
		return this;
	}
}
