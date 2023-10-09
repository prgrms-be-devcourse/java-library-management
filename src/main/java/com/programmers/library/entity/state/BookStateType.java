package com.programmers.library.entity.state;

import java.time.LocalDateTime;
import java.util.function.Supplier;

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
