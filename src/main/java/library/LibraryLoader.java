package library;

import library.book.infra.console.input.ConsoleInputHandler;
import library.book.infra.console.input.InputHandler;
import library.book.infra.console.output.ConsoleOutputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.BookController;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.proxy.BookClient;
import library.book.presentation.proxy.BookExceptionHandler;

public abstract class LibraryLoader {

	private LibraryLoader() {}

	public static BookController assembleBookController() {
		InputHandler inputHandler = new ConsoleInputHandler();
		OutputHandler outputHandler = new ConsoleOutputHandler();
		InputConverter inputConverter = new InputConverter();

		return new BookExceptionHandler(
			new BookClient(inputHandler, outputHandler, inputConverter),
			outputHandler
		);
	}
}
