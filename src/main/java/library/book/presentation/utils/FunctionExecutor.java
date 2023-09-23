package library.book.presentation.utils;

import library.book.application.BookManageService;
import library.book.application.dto.request.RegisterBookRequest;

public class FunctionExecutor {

	private final BookManageService bookManageService;
	private final ConsoleProcessor consoleProcessor;

	public FunctionExecutor(
		final BookManageService bookManageService,
		final ConsoleProcessor consoleProcessor
	) {
		this.bookManageService = bookManageService;
		this.consoleProcessor = consoleProcessor;
	}

	public void executeRegisterBook() {
		RegisterBookRequest request = consoleProcessor.inputBookInfo();

		bookManageService.registerBook(request);
	}
}
