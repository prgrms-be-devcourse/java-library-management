package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.exception.BookException;
import library.book.exception.ErrorCode;

public class Status {

	private BookStatus bookStatus;

	private LocalDateTime cleaningEndTime;

	//== Factory 메소드 ==//
	public Status() {
		this.bookStatus = AVAILABLE_RENT;
		this.cleaningEndTime = null;
	}

	public enum BookStatus {

		AVAILABLE_RENT("대여 가능", ALREADY_AVAILABLE_RENT),
		RENTED("대여중", ALREADY_RENTED),
		CLEANING("정리중", NOW_CLEANING),
		LOST("분실", NOW_LOST);

		private final String description;
		private final ErrorCode errorCode;

		BookStatus(
			final String description,
			final ErrorCode errorCode
		) {
			this.description = description;
			this.errorCode = errorCode;
		}

		public String getDescription() {
			return description;
		}
	}

	//== Utility 메소드 ==//
	public BookStatus getBookStatus() {
		checkCleaningComplete();
		return bookStatus;
	}

	public LocalDateTime getCleaningEndTime() {
		return cleaningEndTime;
	}

	//== Business 메소드 ==//
	public void rent() {
		checkCleaningComplete();
		validateIsAbleToRent();
		this.bookStatus = RENTED;
	}

	public void returnBook() {
		validateIsAbleToReturn();
		this.bookStatus = CLEANING;
		this.cleaningEndTime = LocalDateTime.now().plusMinutes(5);
	}

	private void validateIsAbleToReturn() {
		if (bookStatus.equals(AVAILABLE_RENT)) {
			throw BookException.of(bookStatus.errorCode);
		}
	}

	private void validateIsAbleToRent() {
		if (!bookStatus.equals(AVAILABLE_RENT)) {
			throw BookException.of(bookStatus.errorCode);
		}
	}

	private void checkCleaningComplete() {
		if (bookStatus.equals(CLEANING)) {
			validateCleaningEndTimeIsNotNull();
			updateCleaningStatus();
		}
	}

	private void validateCleaningEndTimeIsNotNull() {
		if (cleaningEndTime == null) {
			throw BookException.of(INVALID_CLEANING_END_TIME);
		}
	}

	private void updateCleaningStatus() {
		if (cleaningEndTime.isBefore(LocalDateTime.now())) {
			this.cleaningEndTime = null;
			this.bookStatus = AVAILABLE_RENT;
		}
	}
}
