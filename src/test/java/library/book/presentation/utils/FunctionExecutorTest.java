package library.book.presentation.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.mock.MockBookService;
import library.book.mock.MockConsoleProcessor;

@DisplayName("[FunctionExecutor Test] - Presentation")
class FunctionExecutorTest {

	private final FunctionExecutor functionExecutor = new FunctionExecutor(
		new MockBookService(), new MockConsoleProcessor(null, null, null)
	);

	@Test
	@DisplayName("[executeRegisterBook 테스트]")
	void executeRegisterBookTest() {
		//when
		Executable when = functionExecutor::executeRegisterBook;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[executeSearchAllBooks 테스트]")
	void executeSearchAllBooksTest() {
		//when
		Executable when = functionExecutor::executeSearchAllBooks;

		//then
		assertDoesNotThrow(when);
	}
}
