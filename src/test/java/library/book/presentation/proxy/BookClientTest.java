package library.book.presentation.proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.manager.BookClient;
import library.book.stub.StubBookController;
import library.book.stub.StubInputHandler;

@DisplayName("[BookClient Test] - Presentation")
class BookClientTest {

	private final BookClient bookClient = new BookClient(
		new StubInputHandler(), new ConsoleOutputHandler()
	);

	@Test
	@DisplayName("[실제 도서 기능을 실행시킨다]")
	void runTest() throws Exception {
		//given
		Field field = bookClient.getClass().getDeclaredField("target");
		field.setAccessible(true);
		field.set(bookClient, new StubBookController());

		//when
		Executable when = bookClient::run;

		//then
		assertDoesNotThrow(when);
	}
}
