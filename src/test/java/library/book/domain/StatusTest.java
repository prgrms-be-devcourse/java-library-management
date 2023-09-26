package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.domain.Status.BookStatus;
import library.book.exception.BookException;

@DisplayName("[Status Test] - Domain")
class StatusTest {

	@Test
	@DisplayName("[생성자 테스트]")
	void constructorTest() {
		//when
		Status status = new Status();

		//then
		assertAll(
			() -> assertThat(status.getBookStatus()).isEqualTo(AVAILABLE_RENT),
			() -> assertThat(status.getCleaningEndTime()).isNull()
		);
	}

	@Nested
	@DisplayName("[updateBookStatusToRented 테스트]")
	class updateBookStatusToRentedTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			Status status = new Status();

			//when
			Executable when = status::rent;

			//then
			assertDoesNotThrow(when);
		}

		@Test
		@DisplayName("[Success] 상태가 정리중인데 정리종료시간이 지나서 성공한다.")
		void successWhenStatusIsCleaningAndAfterEndTime() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, CLEANING);
			setEndTime(status, -2);

			//when
			Executable when = status::rent;

			//then
			assertDoesNotThrow(when);
		}

		@Test
		@DisplayName("[Fail] 상태가 정리중인데 종료시간이 설정돼있지 않아서 실패한다.")
		void failWhenStatusIsCleaningAndEndTimeIsNull() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, CLEANING);

			//when
			ThrowingCallable when = status::rent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(INVALID_CLEANING_END_TIME.getMessage());
		}

		@Test
		@DisplayName("[Fail] 상태가 대여중 이어서 실패한다.")
		void failWhenStatusIsRented() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, RENTED);

			//when
			ThrowingCallable when = status::rent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_RENTED.getMessage());
		}

		@Test
		@DisplayName("[Fail] 상태가 분실됨 이어서 실패한다.")
		void failWhenStatusIsLost() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, LOST);

			//when
			ThrowingCallable when = status::rent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_LOST.getMessage());
		}

		@Test
		@DisplayName("[Fail] 상태가 정리중 이어서 실패한다.")
		void failWhenStatusIsCleaning() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, CLEANING);
			setEndTime(status, 2);

			//when
			ThrowingCallable when = status::rent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_CLEANING.getMessage());
		}
	}

	@Nested
	@DisplayName("[returnBook 테스트]")
	class returnBookTest {

		@Test
		@DisplayName("[Success]")
		void success() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, RENTED);

			//when
			status.returnBook();

			//then
			assertAll(
				() -> assertThat(status.getBookStatus()).isEqualTo(CLEANING),
				() -> {
					Duration duration = Duration.between(status.getCleaningEndTime(), LocalDateTime.now());
					long minutesDifference = Math.abs(duration.toMinutes());

					assertThat(minutesDifference).isEqualTo(5);
				}
			);
		}

		@Test
		@DisplayName("[Fail] 이미 대여 가능한 상태여서 실패한다")
		void failWhenBookStatusIsAvailableRent() {
			//given
			Status status = new Status();

			//when
			ThrowingCallable when = status::returnBook;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_AVAILABLE_RENT.getMessage());
		}
	}

	@Nested
	@DisplayName("[registerAsLost 테스트]")
	class registerAsLostTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			Status status = new Status();

			//when
			status.registerAsLost();

			//then
			assertAll(
				() -> assertThat(status.getBookStatus()).isEqualTo(LOST),
				() -> assertThat(status.getCleaningEndTime()).isNull()
			);
		}

		@Test
		@DisplayName("[Fail] 상태가 분실 항태여서 실패한다")
		void failWhenStatusIsLost() throws Exception {
			//given
			Status status = new Status();
			setBookStatus(status, LOST);

			//when
			ThrowingCallable when = status::registerAsLost;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_LOST.getMessage());
		}
	}

	private void setBookStatus(final Status status, final BookStatus bookStatus) throws Exception {
		Field field = status.getClass().getDeclaredField("bookStatus");
		field.setAccessible(true);
		field.set(status, bookStatus);
	}

	private void setEndTime(final Status status, final long minutes) throws Exception {
		Field field = status.getClass().getDeclaredField("cleaningEndTime");
		field.setAccessible(true);
		field.set(status, LocalDateTime.now().plusMinutes(minutes));
	}
}
