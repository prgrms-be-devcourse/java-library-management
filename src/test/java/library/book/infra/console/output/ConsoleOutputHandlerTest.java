package library.book.infra.console.output;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@DisplayName("[ConsoleInputHandler Test] - Infra")
public class ConsoleOutputHandlerTest {

	private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

	@Test
	@DisplayName("[모드 선택창을 출력한다]")
	void showSelectModeTest() {
		//when
		Executable when = consoleOutputHandler::showSelectMode;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[시스템 메세지를 출력한다]")
	void showSystemMessageTest() {
		//given
		final String message = "hello";

		//when
		Executable when = () -> consoleOutputHandler.showSystemMessage(message);

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[기능 선택창을 출력한다]")
	void showSelectFunctionTest() {
		//when
		Executable when = consoleOutputHandler::showSelectFunction;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[입력 prefix 를 출력한다]")
	void showInputPrefixTest() {
		//when
		Executable when = consoleOutputHandler::showInputPrefix;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[수평 구분선을 출력한다]")
	void showHorizontalLineTest() {
		//when
		Executable when = consoleOutputHandler::showHorizontalLine;

		//then
		assertDoesNotThrow(when);
	}
}
