package library.book.presentation;

import static library.book.exception.ErrorCode.*;

import library.book.exception.BookException;
import library.book.presentation.io.IoProcessor;
import library.book.presentation.io.OutputHandler;
import library.book.presentation.utils.FunctionExecutor;
import library.book.presentation.utils.FunctionManger;

public class BookController {

	private final FunctionExecutor executor;
	private final IoProcessor ioProcessor;

	public BookController(
		final FunctionExecutor executor,
		final IoProcessor ioProcessor
	) {
		this.executor = executor;
		this.ioProcessor = ioProcessor;
	}

	public void run() {
		FunctionManger functionType = FunctionManger.valueOf(inputFunction());

		functionType.call(executor);
	}

	private String inputFunction() {
		try {
			return ioProcessor.inputNumber(OutputHandler::showSelectFunction);
		} catch (IllegalArgumentException e) {
			throw BookException.of(NOT_SUPPORT_FUNCTION);
		}
	}
}
