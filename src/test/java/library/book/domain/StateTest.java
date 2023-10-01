package library.book.domain;

import static library.book.domain.constants.BookState.*;
import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.domain.constants.BookState;
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
		@DisplayName("[정리중 상태이고 아직 정리가 끝나지 않아서 검증에 실패한다]")
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

		@Test
		@DisplayName("[정리중 상태여서 검증에 실패한다]")
		void failWhenStateIsCleaning() {
			State state = new Cleaning();

			//when
			ThrowingCallable when = state::validateIsAbleToReturn;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(NOW_CLEANING.getMessage());
		}
	}

	@Nested
	@DisplayName("[분실처리 가능한지 검증한다]")
	class validateIsAbleToLost {

		@Test
		@DisplayName("[이미 분실처리 상태여서 검증에 실패한다]")
		void failWhenStateIsAvailableRent() {
			//given
			State state = new Lost();

			//when
			ThrowingCallable when = state::validateIsAbleToLost;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ALREADY_LOST.getMessage());
		}
	}

	@Nested
	@DisplayName("[정리중 상태일 도서 상태를 조회한다]")
	class getBookState {

		@Test
		@DisplayName("[정리 시간이 지난 후에 state 를 조회하면 Available_Rent state 를 리턴한다]")
		void returnAvailableRentWhenAfterCleaning() throws Exception {
			//given
			Cleaning state = new Cleaning();
			setEndAt(state, -10);

			//when
			BookState result = state.getBookState();

			//then
			assertThat(result).isEqualTo(AVAILABLE_RENT);
		}

		@Test
		@DisplayName("[정리 시간이 지나지 않아 Cleaning state 를 리턴한다]")
		void returnCleaningWhenBeforeCleaning() {
			//given
			Cleaning state = new Cleaning();

			//when
			BookState result = state.getBookState();

			//then
			assertThat(result).isEqualTo(CLEANING);
		}
	}

	private void setEndAt(final Cleaning state, final long minutes) throws Exception {
		Field field = state.getClass().getDeclaredField("cleaningEndAt");
		field.setAccessible(true);
		field.set(state, LocalDateTime.now().plusMinutes(minutes));
	}
}
