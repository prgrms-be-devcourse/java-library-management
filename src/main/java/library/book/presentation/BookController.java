package library.book.presentation;

import static library.book.exception.ErrorCode.*;

import library.book.exception.BookException;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.utils.InOutProcessor;
import library.book.presentation.utils.FunctionExecutor;
import library.book.presentation.utils.FunctionManger;

public class BookController {

	private final FunctionExecutor executor;
	private final InOutProcessor inOutProcessor;

	public BookController(
		final FunctionExecutor executor,
		final InOutProcessor inOutProcessor
	) {
		this.executor = executor;
		this.inOutProcessor = inOutProcessor;
	}

	public void run() {
		FunctionManger functionType = FunctionManger.valueOf(inputFunction());

		functionType.call(executor);
	}

	private String inputFunction() {
		try {
			return inOutProcessor.inputNumber(OutputHandler::showSelectFunction);
		} catch (IllegalArgumentException e) {
			throw BookException.of(NOT_SUPPORT_FUNCTION);
		}
	}
}
