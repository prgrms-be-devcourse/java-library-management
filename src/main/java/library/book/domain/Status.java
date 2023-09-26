package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;

import java.time.LocalDateTime;

import library.book.exception.BookException;

public class Status {

	private BookStatus bookStatus;

	private LocalDateTime cleaningEndTime;

	//== Factory 메소드 ==//
	public Status() {
		this.bookStatus = AVAILABLE_RENT;
		this.cleaningEndTime = null;
	}

	public enum BookStatus {

		AVAILABLE_RENT("대여 가능"),
		RENTED("대여중"),
		CLEANING("정리중"),
		LOST("분실");

		private final String description;

		BookStatus(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	//== Utility 메소드 ==//
	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public LocalDateTime getCleaningEndTime() {
		return cleaningEndTime;
	}

	//== Business 메소드 ==//
	public void updateBookStatusToRented() {
		checkCleaningComplete();
		validateIsAbleToRent();
		this.bookStatus = RENTED;
	}

	private void validateIsAbleToRent() {
		switch (bookStatus) {
			case RENTED -> throw BookException.of(ALREADY_RENTED);
			case LOST -> throw BookException.of(NOW_LOST);
			case CLEANING -> throw BookException.of(NOW_CLEANING);
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
