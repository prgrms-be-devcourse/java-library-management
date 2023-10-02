package library.book.infra.console.input;

import static library.book.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import library.book.exception.BookException;

@DisplayName("[ConsoleInputHandler Test] - Infra")
class InputConsoleTest {

	private final ConsoleInputHandler inputConsole = new ConsoleInputHandler();

	@Nested
	@DisplayName("[숫자를 입력받는다]")
	class inputNumberTest {

		@Test
		@DisplayName("[숫자를 성공적으로 입력받는다]")
		void success() {
			//given
			final String input = "2";
			System.setIn(new ByteArrayInputStream(input.getBytes()));

			//when
			int result = inputConsole.inputNumber();

			//then
			assertThat(result).isEqualTo(Integer.valueOf(input));
		}

		@Test
		@DisplayName("문자를 입력하여 실패한다")
		void failWhenInputString() {
			//given
			final String input = "2two";
			System.setIn(new ByteArrayInputStream(input.getBytes()));

			//when
			ThrowingCallable when = inputConsole::inputNumber;

			//then
			assertThatThrownBy(when)
				.isInstanceOf(BookException.class)
				.hasMessageContaining(ONLY_NUMBER.getMessage());
		}
	}

	@Test
	@DisplayName("[문자열을 입력받는다]")
	void inputStringTest() {
		//given
		final String input = "hello";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		//when
		String result = inputConsole.inputString();

		//then
		assertThat(result).isEqualTo("hello");
	}
}
