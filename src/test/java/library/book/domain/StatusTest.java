package library.book.domain;

import static library.book.domain.Status.BookStatus.*;
import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
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
			setStatus(status, CLEANING);
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
			setStatus(status, CLEANING);

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
			setStatus(status, RENTED);

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
			setStatus(status, LOST);

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
			setStatus(status, CLEANING);
			setEndTime(status, 2);

			//when
			ThrowingCallable when = status::rent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_CLEANING.getMessage());
		}
	}

	//todo : 추후에 status 변경 로직이 구현되면 리플렉션 코드 제거
	private void setStatus(final Status status, final BookStatus bookStatus) throws Exception {
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
