package library.book.domain;

import java.time.LocalDateTime;

public class Status {

	private BookStatus status;

	private LocalDateTime cleaningStartTime;

	public enum BookStatus {

		AVAILABLE_RENT,
		RENTED,
		CLEANING,
		LOST
	}

	//== Utility 메소드 ==//
	public BookStatus getStatus() {
		return status;
	}

	public LocalDateTime getCleaningStartTime() {
		return cleaningStartTime;
	}
}
