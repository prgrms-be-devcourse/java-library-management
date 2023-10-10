package com.programmers.library.entity.state;

import java.time.LocalDateTime;

import com.programmers.library.entity.BookStateType;
import com.programmers.library.exception.BookException;
import com.programmers.library.exception.ErrorCode;

public class OrganizingState implements State {

	private LocalDateTime returnedAt;

	public OrganizingState() {
	}

	public OrganizingState(LocalDateTime returnedAt) {
		this.returnedAt = returnedAt;
	}

	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}

	@Override
	public BookStateType getType() {
		return BookStateType.ORGANIZING;
	}

	@Override
	public void borrow() {
		throw new BookException(ErrorCode.BOOK_UNDER_ORGANIZING);
	}

	@Override
	public void returned() {
		throw new BookException(ErrorCode.BOOK_UNDER_ORGANIZING);
	}

	@Override
	public void lost() {
		// success
	}

	@Override
	public State organize() {
		if (returnedAt.plusMinutes(5).isBefore(LocalDateTime.now())) {
			return new AvailableState();
		} else {
			return this;
		}
	}
}
