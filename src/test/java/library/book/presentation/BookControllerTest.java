package library.book.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.stub.StubIoProcessor;
import library.book.stub.StubFunctionExecutor;

@DisplayName("[BookController Test] - Presentation")
public class BookControllerTest {

	private final BookController bookController = new BookController(
		new StubFunctionExecutor(null, null),
		new StubIoProcessor(null, null, null)
	);

	@Test
	@DisplayName("[도서 관리 프로그램을 실행시킨다]")
	void runTest() {
		//when
		Executable when = bookController::run;

		//then
		assertDoesNotThrow(when);
	}
}
