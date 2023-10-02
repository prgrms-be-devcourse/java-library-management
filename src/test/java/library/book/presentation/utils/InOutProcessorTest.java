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

@DisplayName("[InOutProcessor Test] - Presentation")
class InOutProcessorTest {

	private final InOutProcessor inOutProcessor = new InOutProcessor(
		new StubInputHandler(), new ConsoleOutputHandler(), new InputConverter()
	);

	@Test
	@DisplayName("[문자열을 입력받는다]")
	void inputStringTest() {
		//when
		String result = inOutProcessor.inputString();

		//then
		assertThat(result).isEqualTo("hello");
	}

	@Test
	@DisplayName("[숫자를 입력받아 정해진 문자열로 변환한다]")
	void inputModeNumberTest() {
		//when
		String result = inOutProcessor.inputNumber(OutputHandler::showSelectFunction);

		//then
		assertThat(result).isEqualTo("ONE");
	}

	@Test
	@DisplayName("[도서 정보를 입력받는다]")
	void inputBookInfoTest() {
		//when
		RegisterBookRequest result = inOutProcessor.inputBookInfo();

		//then
		assertAll(
			() -> assertThat(result.title()).isEqualTo("hello"),
			() -> assertThat(result.authorName()).isEqualTo("hello"),
			() -> assertThat(result.pages()).isEqualTo(1)
		);
	}

	@Test
	@DisplayName("[도서 정보를 출력한다]")
	void outputBookInfoTest() {
		//given
		List<BookSearchResponse> responses = Arrays.stream(BookFixture.values())
			.map(BookFixture::toSearchResponse)
			.toList();

		//when
		Executable when = () -> inOutProcessor.outputBookInfo(
			responses, ENTRY_SEARCH_ALL_BOOKS.getValue(), COMPLETE_SEARCH_ALL_BOOKS.getValue()
		);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[도서 번호를 입력받는다]")
	void inputBookId() {
		//when
		Executable when = () -> inOutProcessor.inputBookId(ENTRY_RENT_BOOK, INPUT_RENT_BOOK_ID);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[완료 메세지를 출력한다]")
	void outputCompleteMessageTest() {
		//when
		Executable when = () -> inOutProcessor.outputCompleteMessage(COMPLETE_RENT);

		//then
		assertDoesNotThrow(when);
	}
}
