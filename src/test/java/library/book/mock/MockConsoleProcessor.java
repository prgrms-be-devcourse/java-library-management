package library.book.mock;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.InputConsole;
import library.book.infra.console.output.OutputConsole;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.utils.ConsoleProcessor;

public class MockConsoleProcessor extends ConsoleProcessor {

	public MockConsoleProcessor(
		InputConsole inputConsole,
		OutputConsole outputConsole,
		InputConverter converter
	) {
		super(inputConsole, outputConsole, converter);
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
