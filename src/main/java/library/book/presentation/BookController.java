package library.book.presentation;

import library.book.presentation.utils.ConsoleProcessor;
import library.book.presentation.utils.FunctionExecutor;
import library.book.presentation.utils.FunctionManger;

public class BookController {

	private final FunctionExecutor executor;
	private final ConsoleProcessor consoleProcessor;

	public BookController(
		final FunctionExecutor executor,
		final ConsoleProcessor consoleProcessor
	) {
		this.executor = executor;
		this.consoleProcessor = consoleProcessor;
	}

	public void run() {
		FunctionManger functionType = FunctionManger.valueOf(consoleProcessor.inputFunctionNumber());

		functionType.call(executor);
	}
}
