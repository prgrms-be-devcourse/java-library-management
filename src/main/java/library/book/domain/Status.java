package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.exception.BookException;

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

	//== Business 메소드 ==//
	public void updateBookStatusToRented() {
		validateIsAbleToRent();
		this.bookStatus = RENTED;
	}

	private void validateIsAbleToRent() {
		switch (this.bookStatus) {
			case RENTED -> throw BookException.of(ALREADY_RENTED);
			case LOST -> throw BookException.of(NOW_LOST);
			case CLEANING -> throw BookException.of(NOW_CLEANING);
		}
	}
}
