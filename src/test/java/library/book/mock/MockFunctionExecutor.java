package library.book.mock;

import library.book.application.BookService;
import library.book.presentation.utils.ConsoleProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class MockFunctionExecutor extends FunctionExecutor {

	public MockFunctionExecutor(
		final BookService bookService,
		final ConsoleProcessor consoleProcessor
	) {
		super(bookService, consoleProcessor);
	}

	@Override
	public void executeRegisterBook() {

	}
}
