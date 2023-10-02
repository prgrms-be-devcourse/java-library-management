package library.book.stub;

import library.book.application.BookService;
import library.book.presentation.io.IoProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class StubFunctionExecutor extends FunctionExecutor {

	public StubFunctionExecutor(
		final BookService bookService,
		final IoProcessor ioProcessor
	) {
		super(bookService, ioProcessor);
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

	@Override
	public void executeReturnBook() {
		System.out.println("[call executeReturnBook]");
	}

	@Override
	public void executeRegisterAsLost() {
		System.out.println("[call executeRegisterAsLost]");
	}

	@Override
	public void executeDeleteBook() {
		System.out.println("[call executeDeleteBook]");
	}
}
