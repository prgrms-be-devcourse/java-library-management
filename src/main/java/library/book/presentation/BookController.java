package library.book.presentation;

import static library.book.exception.ErrorCode.*;

import library.book.exception.BookException;
import library.book.infra.console.output.OutputHandler;
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
		FunctionManger functionType = FunctionManger.valueOf(inputFunction());

		functionType.call(executor);
	}

	private String inputFunction() {
		try {
			return consoleProcessor.inputNumber(OutputHandler::showSelectFunction);
		} catch (IllegalArgumentException e) {
			throw BookException.of(NOT_SUPPORT_FUNCTION);
		}
	}
}
