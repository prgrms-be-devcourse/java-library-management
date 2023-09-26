package library.book.presentation.proxy;

import library.book.exception.BookException;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.BookController;

public class BookExceptionHandler extends BookController {

	private final BookController target;
	private final OutputHandler outputHandler;

	public BookExceptionHandler(
		final BookController target,
		final OutputHandler outputHandler
	) {
		super(null, null);
		this.target = target;
		this.outputHandler = outputHandler;
	}

	@Override
	public void run() {
		try {
			target.run();
		} catch (BookException e) {
			outputHandler.showSystemMessage("\n[System] " + e.getMessage() + "\n");
		}
	}
}
