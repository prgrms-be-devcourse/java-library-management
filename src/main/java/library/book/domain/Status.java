package library.book.domain;

import static library.book.domain.Status.BookStatus.*;

import java.time.LocalDateTime;

public class Status {

	private BookStatus bookStatus;

	private LocalDateTime cleaningStartTime;

	public enum BookStatus {

		AVAILABLE_RENT,
		RENTED,
		CLEANING,
		LOST
	}

	//== Factory 메소드 ==//
	public Status() {
		this.bookStatus = AVAILABLE_RENT;
		this.cleaningStartTime = null;
	}

	//== Utility 메소드 ==//
	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public LocalDateTime getCleaningStartTime() {
		return cleaningStartTime;
	}
}
