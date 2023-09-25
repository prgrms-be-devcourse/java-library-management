package library.book.presentation.converter;

import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.exception.BookException;

@DisplayName("[InputConverter Test] - Presentation")
public class InputConverterTest {

	private final InputConverter inputConverter = new InputConverter();

	@Test
	@DisplayName("[convertNumberToString 테스트]")
	void convertNumberToString() {
		//given
		final int number = 2;

		//when
		String result = inputConverter.convertNumberToString(number);

		//then
		assertThat(result).isEqualTo("TWO");
	}

	@Nested
	@DisplayName("[convertStringToRegisterRequest 테스트]")
	class convertStringToRegisterRequestTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			final String bookInfo = "hello|||hello|||100";

			//when
			RegisterBookRequest result = inputConverter.convertStringToRegisterRequest(bookInfo);

			//then
			assertAll(
				() -> assertThat(result.title()).isEqualTo("hello"),
				() -> assertThat(result.authorName()).isEqualTo("hello"),
				() -> assertThat(result.pages()).isEqualTo(100)
			);
		}

		@Test
		@DisplayName("[Fail] 잘못된 도서 정보 입력형식으로 실패한다")
		void failWhenInvalidBookInfoFormat() {
			//given
			final String invalidBookInfo = "hello|||he|||llo|||100";

			//when
			ThrowingCallable when = () -> inputConverter.convertStringToRegisterRequest(invalidBookInfo);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(INVALID_BOOK_INFO_FORMAT.getMessage());
		}
	}
}
