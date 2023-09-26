package library.book.presentation.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.stub.StubBookService;
import library.book.stub.StubConsoleProcessor;

@DisplayName("[FunctionExecutor Test] - Presentation")
class FunctionExecutorTest {

	private final FunctionExecutor functionExecutor = new FunctionExecutor(
		new StubBookService(), new StubConsoleProcessor(null, null, null)
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

	@Test
	@DisplayName("[executeSearchBooksByTitle 테스트]")
	void executeSearchBooksByTitleTest() {
		//when
		Executable when = functionExecutor::executeSearchBooksByTitle;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[executeRentBook 테스트]")
	void executeRentBookTest() {
		//when
		Executable when = functionExecutor::executeRentBook;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[executeReturnBook 테스트]")
	void executeReturnBookTest() {
		//when
		Executable when = functionExecutor::executeReturnBook;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[executeRegisterAsLost 테스트]")
	void executeRegisterAsLost() {
		//when
		Executable when = functionExecutor::executeRegisterAsLost;

		//then
		assertDoesNotThrow(when);
	}
}
