package com.programmers.library.entity;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import com.programmers.library.entity.state.AvailableState;
import com.programmers.library.entity.state.BorrowedState;
import com.programmers.library.entity.state.LostState;
import com.programmers.library.entity.state.OrganizingState;
import com.programmers.library.entity.state.State;

public enum BookStateType {
	AVAILABLE("대여 가능", AvailableState::new),
	BORROWED("대여중", BorrowedState::new),
	ORGANIZING("도서 정리중", OrganizingState::new),
	LOST("분실됨", LostState::new);

	private final String value;
	private final Supplier<State> stateSupplier;

	BookStateType(String value, Supplier<State> stateSupplier) {
		this.value = value;
		this.stateSupplier = stateSupplier;
	}

	public String getValue() {
		return value;
	}

	public State getState(LocalDateTime returnedAt) {
		if(this == ORGANIZING) {
			return new OrganizingState(returnedAt);
		}
		return stateSupplier.get();
	}

}
