package library.book.presentation.utils;

import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;

public class FunctionExecutor {

	private final BookService bookService;
	private final ConsoleProcessor consoleProcessor;

	public FunctionExecutor(
		final BookService bookService,
		final ConsoleProcessor consoleProcessor
	) {
		this.bookService = bookService;
		this.consoleProcessor = consoleProcessor;
	}

	public void executeRegisterBook() {
		RegisterBookRequest request = consoleProcessor.inputBookInfo();

		bookService.registerBook(request);
	}
}
