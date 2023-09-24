package library.book.mock;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.infra.console.input.InputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.utils.ConsoleProcessor;

public class MockConsoleProcessor extends ConsoleProcessor {

	public MockConsoleProcessor(
		InputHandler inputHandler,
		OutputHandler outputHandler,
		InputConverter converter
	) {
		super(inputHandler, outputHandler, converter);
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
