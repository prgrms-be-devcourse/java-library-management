package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.fixture.BookFixture;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.converter.InputConverter;
import library.book.stub.StubInputHandler;

@DisplayName("[ConsoleProcessor Test] - Presentation")
class ConsoleProcessorTest {

	private final ConsoleProcessor consoleProcessor = new ConsoleProcessor(
		new StubInputHandler(), new ConsoleOutputHandler(), new InputConverter()
	);

	@Test
	@DisplayName("[inputString 테스트]")
	void inputStringTest() {
		//when
		String result = consoleProcessor.inputString();

		//then
		assertThat(result).isEqualTo("hello");
	}

	@Test
	@DisplayName("[inputNumber 테스트]")
	void inputModeNumberTest() {
		//when
		String result = consoleProcessor.inputNumber(OutputHandler::showSelectFunction);

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

	@Test
	@DisplayName("[outputBookInfo 테스트]")
	void outputBookInfoTest() {
		//given
		List<BookSearchResponse> responses = Arrays.stream(BookFixture.values())
			.map(BookFixture::toSearchResponse)
			.toList();

		//when
		Executable when = () -> consoleProcessor.outputBookInfo(
			responses, ENTRY_SEARCH_ALL_BOOKS.getValue(), COMPLETE_SEARCH_ALL_BOOKS.getValue()
		);

		//
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[inputBookId 테스트]")
	void inputBookId() {
		//when
		Executable when = consoleProcessor::inputBookId;

		//then
		assertDoesNotThrow(when);
	}
}
