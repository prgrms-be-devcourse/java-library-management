package library.book.presentation.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.mock.MockInputHandler;
import library.book.presentation.converter.InputConverter;

@DisplayName("[ConsoleProcessor Test] - Presentation")
class ConsoleProcessorTest {

	private final ConsoleProcessor consoleProcessor = new ConsoleProcessor(
		new MockInputHandler(), new ConsoleOutputHandler(), new InputConverter()
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
		RegisterBookRequest result = consoleProcessor.inputBookInfo();

		//then
		assertAll(
			() -> assertThat(result.title()).isEqualTo("hello"),
			() -> assertThat(result.authorName()).isEqualTo("hello"),
			() -> assertThat(result.pages()).isEqualTo(1)
		);
	}
}
