package library.book.domain.constants;

import java.util.function.Supplier;

import library.book.domain.State;
import library.book.domain.state.AvailableRent;
import library.book.domain.state.Cleaning;
import library.book.domain.state.Lost;
import library.book.domain.state.Rented;

public enum BookState {

	AVAILABLE_RENT("대여 가능", AvailableRent::new),
	RENTED("대여중", Rented::new),
	CLEANING("정리중", Cleaning::new),
	LOST("분실", Lost::new);

	private final String description;
	private final Supplier<State> stateSupplier;

	BookState(final String description, final Supplier<State> stateSupplier) {
		this.description = description;
		this.stateSupplier = stateSupplier;
	}

	public String getDescription() {
		return description;
	}

	public State getState() {
		return stateSupplier.get();
	}
}
