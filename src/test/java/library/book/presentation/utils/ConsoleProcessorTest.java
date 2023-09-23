package library.book.presentation.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import library.book.infra.console.output.OutputConsole;
import library.book.mock.MockInputConsole;
import library.book.presentation.converter.InputConverter;

@DisplayName("[ConsoleProcessor Test] - Presentation")
class ConsoleProcessorTest {

	private final ConsoleProcessor consoleProcessor = new ConsoleProcessor(
		new MockInputConsole(), new OutputConsole(), new InputConverter()
	);

	@Test
	@DisplayName("[inputNumber 테스트]")
	void inputNumberTest() {
		//when
		String result = consoleProcessor.inputFunctionNumber();

		//then
		assertThat(result).isEqualTo("ONE");
	}

	@Test
	@DisplayName("[inputBookInfo 테스트]")
	void inputBookInfoTest() {
		//when
		String result = consoleProcessor.inputBookInfo();

		//then
		assertThat(result).isEqualTo("hello/hello/ONE");
	}
}
