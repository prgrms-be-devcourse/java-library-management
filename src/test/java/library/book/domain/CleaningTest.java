package library.book.domain;

import static library.book.domain.constants.BookState.*;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.domain.constants.BookState;
import library.book.domain.state.Cleaning;

@DisplayName("[Cleaning 테스트] - Domain")
public class CleaningTest {

	@Nested
	@DisplayName("[정리중 상태일 때 조회한다]")
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
