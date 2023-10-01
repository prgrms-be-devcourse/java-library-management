package library.book.domain.constants;

public enum BookState {

	VAILABLE_RENT("대여 가능"),
	RENTED("대여중"),
	CLEANING("정리중"),
	LOST("분실");

	private final String description;

	BookState(final String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
