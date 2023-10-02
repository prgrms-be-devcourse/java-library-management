package library.book.manager;

import library.book.exception.BookException;
import library.book.presentation.BookController;

public class BookExceptionHandler extends BookController {

	private final BookController target;

	public BookExceptionHandler(
		final BookController target
	) {
		super(null, null);
		this.target = target;
	}

	@Override
	public void run() {
		try {
			target.run();
		} catch (BookException e) {
			String newLine = System.lineSeparator();
			System.out.println(newLine + "[System] " + e.getMessage() + newLine);
		}
	}
}
