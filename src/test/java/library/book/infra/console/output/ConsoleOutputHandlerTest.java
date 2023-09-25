package library.book.infra.console.output;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@DisplayName("[ConsoleInputHandler Test] - Infra")
public class ConsoleOutputHandlerTest {

	private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

	@Test
	@DisplayName("[showSelectMode 테스트]")
	void showSelectModeTest() {
		//when
		Executable when = consoleOutputHandler::showSelectMode;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[showSystemMessage 테스트]")
	void showSystemMessageTest() {
		//given
		final String message = "hello";

		//when
		Executable when = () -> consoleOutputHandler.showSystemMessage(message);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[showSelectFunction 테스트]")
	void showSelectFunctionTest() {
		//when
		Executable when = consoleOutputHandler::showSelectFunction;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[showInputPrefix 테스트]")
	void showInputPrefixTest() {
		//when
		Executable when = consoleOutputHandler::showInputPrefix;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[showHorizontalLine 테스트]")
	void showHorizontalLineTest() {
		//when
		Executable when = consoleOutputHandler::showHorizontalLine;

		//then
		assertDoesNotThrow(when);
	}
}
