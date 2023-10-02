package library.book.manager;

import library.book.infra.console.input.ConsoleInputHandler;
import library.book.presentation.io.InputHandler;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.presentation.io.OutputHandler;
import library.book.presentation.BookController;

public abstract class LibraryContextLoader {

	private LibraryContextLoader() {}

	public static BookController assembleBookController() {
		InputHandler inputHandler = new ConsoleInputHandler();
		OutputHandler outputHandler = new ConsoleOutputHandler();

		return new BookExceptionHandler(new BookClient(inputHandler, outputHandler));
	}
}
