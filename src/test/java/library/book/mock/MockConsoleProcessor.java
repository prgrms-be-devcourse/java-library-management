package library.book.mock;

import java.util.List;
import java.util.function.Consumer;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.infra.console.input.InputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.constant.Message;
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
	public RegisterBookRequest inputBookInfo() {
		return new RegisterBookRequest("hello", "hello", 100);
	}

	@Override
	public String inputString() {
		System.out.println("[call inputString()]");
		return "hello";
	}

	@Override
	public String inputNumber(Consumer<OutputHandler> selectConsole) {
		System.out.println("[call inputNumber()]");
		return "ONE";
	}

	@Override
	public void outputBookInfo(
		final List<BookSearchResponse> responses,
		final Message entryMessage
	) {
		System.out.println("[call outputBookInfo()]");
	}
}
