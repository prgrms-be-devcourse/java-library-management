package com.programmers.library.entity.state;

import java.time.LocalDateTime;

import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class OrganizingState implements State {
	@Override
	public BookStateType getType() {
		return BookStateType.ORGANIZING;
	}

	@Override
	public State borrow() {
		throw new BookException(ErrorCode.BOOK_UNDER_ORGANIZING);
	}

	@Override
	public State returned() {
		throw new BookException(ErrorCode.BOOK_UNDER_ORGANIZING);
	}

	@Override
	public State lost() {
		return new LostState();
	}

	@Override
	public State organize(LocalDateTime returnedAt) {
		if(returnedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
			return new AvailableState();
		} else {
			return this;
		}
	}
}
