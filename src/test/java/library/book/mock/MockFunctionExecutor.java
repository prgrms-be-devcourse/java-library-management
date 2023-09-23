package library.book.mock;

import library.book.application.BookManageService;
import library.book.presentation.utils.ConsoleProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class MockFunctionExecutor extends FunctionExecutor {

	public MockFunctionExecutor(
		final BookManageService bookManageService,
		final ConsoleProcessor consoleProcessor
	) {
		super(bookManageService, consoleProcessor);
	}

	@Override
	public void executeRegisterBook() {

	}
}
