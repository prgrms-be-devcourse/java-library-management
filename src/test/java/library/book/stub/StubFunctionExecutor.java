package library.book.stub;

import library.book.application.BookService;
import library.book.presentation.utils.ConsoleProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class StubFunctionExecutor extends FunctionExecutor {

	public StubFunctionExecutor(
		final BookService bookService,
		final ConsoleProcessor consoleProcessor
	) {
		super(bookService, consoleProcessor);
	}

	@Override
	public void executeRegisterBook() {
		System.out.println("[call executeRegisterBook]");
	}

	@Override
	public void executeSearchAllBooks() {
		System.out.println("[call executeSearchAllBooks]");
	}

	@Override
	public void executeSearchBooksByTitle() {
		System.out.println("[call executeSearchBooksByTitle]");
	}

	@Override
	public void executeRentBook() {
		System.out.println("[call executeRentBook]");
	}
}
