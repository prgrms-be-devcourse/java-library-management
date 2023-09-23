package library.book.infra.console.output;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@DisplayName("[InputConsole Test] - Infra")
public class OutputConsoleTest {

	private final OutputConsole outputConsole = new OutputConsole();

	@Test
	@DisplayName("[showSelectMode 테스트]")
	void showSelectModeTest() {
		//when
		Executable when = outputConsole::showSelectMode;

		//then
		assertDoesNotThrow(when);
	}

	@Test
	@DisplayName("[showSystemMessage 테스트]")
	void showSystemMessageTest() {
		//given
		final String message = "hello";

		//when
		Executable when = () -> outputConsole.showSystemMessage(message);

		//then
		assertDoesNotThrow(when);
	}
}
