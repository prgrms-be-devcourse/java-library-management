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
	@DisplayName("[숫자(번호)를 정해진 문자열로 변환한다]")
	void convertNumberToString() {
		//given
		final int number = 2;

		//when
		String result = inputConverter.convertNumberToString(number);

		//then
		assertThat(result).isEqualTo("TWO");
	}

	@Nested
	@DisplayName("[도서 정보를 담은 문자열을 도서 등록요청 객체로 변환한다]")
	class convertStringToRegisterRequestTest {

		@Test
		@DisplayName("[성공적으로 변환한다]")
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
		@DisplayName("[도서 정보를 담은 문자열의 형식이 잘못되어 실패한다]")
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
