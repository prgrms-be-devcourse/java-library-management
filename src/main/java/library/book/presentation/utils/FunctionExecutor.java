package library.book.presentation.utils;

import java.util.List;

import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;

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

	public void executeSearchAllBooks() {
		List<BookSearchResponse> responses = bookService.searchBooks();
		consoleProcessor.outputBookInfo(responses);
	}
}
