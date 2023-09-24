package library.book.mock;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.ConsoleInputHandler;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.utils.ConsoleProcessor;

public class MockConsoleProcessor extends ConsoleProcessor {

	public MockConsoleProcessor(
		ConsoleInputHandler inputConsole,
		ConsoleOutputHandler consoleOutputHandler,
		InputConverter converter
	) {
		super(inputConsole, consoleOutputHandler, converter);
	}

	@Override
	public String inputFunctionNumber() {
		return "ONE";
	}

	@Override
	public RegisterBookRequest inputBookInfo() {
		return new RegisterBookRequest("hello", "hello", 100);
	}
}
