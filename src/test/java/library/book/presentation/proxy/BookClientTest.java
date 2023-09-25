package library.book.presentation.proxy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.mock.MockInputHandler;
import library.book.presentation.converter.InputConverter;

@DisplayName("[BookClient Test] - Presentation")
class BookClientTest {

	private final BookClient bookClient = new BookClient(
		new MockInputHandler(), new ConsoleOutputHandler(), new InputConverter()
	);

	@Test
	@DisplayName("[run 테스트]")
	void runTest() {
		//when
		Executable when = bookClient::run;

		//then
		assertDoesNotThrow(when);
	}
}
