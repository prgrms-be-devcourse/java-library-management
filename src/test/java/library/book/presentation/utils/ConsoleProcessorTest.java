package library.book.presentation.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import library.book.infra.console.output.OutputConsole;
import library.book.mock.MockInputConsole;
import library.book.presentation.converter.NumberConverter;

@DisplayName("[ConsoleProcessor Test] - Presentation")
public class ConsoleProcessorTest {

	private final ConsoleProcessor consoleProcessor = new ConsoleProcessor(
		new MockInputConsole(), new OutputConsole(), new NumberConverter()
	);

	@Test
	@DisplayName("[inputNumber 테스트]")
	void inputNumberTest() {
		//when
		String result = consoleProcessor.inputFunctionNumber();

		//then
		assertThat(result).isEqualTo("ONE");
	}
}
