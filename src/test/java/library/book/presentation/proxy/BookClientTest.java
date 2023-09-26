package library.book.presentation.proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.stub.StubBookController;
import library.book.stub.StubInputHandler;
import library.book.presentation.converter.InputConverter;

@DisplayName("[BookClient Test] - Presentation")
class BookClientTest {

	private final BookClient bookClient = new BookClient(
		new StubInputHandler(), new ConsoleOutputHandler(), new InputConverter()
	);

	@Test
	@DisplayName("[run 테스트]")
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
