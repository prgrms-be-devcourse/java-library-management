package library.book.domain;

import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.domain.state.AvailableRent;
import library.book.domain.state.Cleaning;
import library.book.domain.state.Lost;
import library.book.domain.state.Rented;
import library.book.exception.BookException;

@DisplayName("[State 테스트] - Domain")
class StateTest {

	@Nested
	@DisplayName("[대여 가능한지 검증한다]")
	class validateIsAbleToRent {

		@Test
		@DisplayName("[정리중 상태여서 검증에 실패한다]")
		void failWhenStateIsCleaning() {
			//given
			State state = new Cleaning();

			//when
			ThrowingCallable when = state::validateIsAbleToRent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_CLEANING.getMessage());
		}

		@Test
		@DisplayName("[분실 상태여서 검증에 실패한다]")
		void failWhenStateIsLost() {
			//given
			State state = new Lost();

			//when
			ThrowingCallable when = state::validateIsAbleToRent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_LOST.getMessage());
		}

		@Test
		@DisplayName("[대여중 상태여서 검증에 실패한다]")
		void failWhenStateIsRented() {
			//given
			State state = new Rented();

			//when
			ThrowingCallable when = state::validateIsAbleToRent;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_RENTED.getMessage());
		}
	}

	@Nested
	@DisplayName("[반납 가능한지 검증한다]")
	class validateIsAbleToReturn {

		@Test
		@DisplayName("[이미 대여가 가능한 상태여서 검증에 실패한다]")
		void failWhenStateIsAvailableRent() {
			//given
			State state = new AvailableRent();

			//when
			ThrowingCallable when = state::validateIsAbleToReturn;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_AVAILABLE_RENT.getMessage());
		}
	}
}
