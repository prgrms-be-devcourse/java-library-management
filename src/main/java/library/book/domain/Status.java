package library.book.domain;

import static library.book.domain.Status.BookStatus.*;

import java.time.LocalDateTime;

public class Status {

	private BookStatus bookStatus;

	private LocalDateTime cleaningStartTime;

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

	public enum BookStatus {

		AVAILABLE_RENT("대여 가능"),
		RENTED("대여중"),
		CLEANING("정리중"),
		LOST("분실")
		;

		private final String description;

		BookStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
}
