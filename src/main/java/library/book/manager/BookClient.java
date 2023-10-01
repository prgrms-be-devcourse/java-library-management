package library.book.manager;

import static library.book.exception.ErrorCode.*;

import library.book.application.BookService;
import library.book.application.DefaultBookService;
import library.book.domain.BookRepository;
import library.book.exception.BookException;
import library.book.infra.console.input.InputHandler;
import library.book.infra.console.output.OutputHandler;
import library.book.presentation.BookController;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.utils.ConsoleProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class BookClient extends BookController {

	private final BookController target;

	public BookClient(
		final InputHandler inputHandler,
		final OutputHandler outputHandler
	) {
		super(null, null);

		InputConverter converter = new InputConverter();

		ConsoleProcessor consoleProcessor = new ConsoleProcessor(inputHandler, outputHandler, converter);

		String mode = inputMode(consoleProcessor);

		BookRepository bookRepository = ModeManager.valueOf(mode).getRepository();
		BookService bookService = new DefaultBookService(bookRepository);

		FunctionExecutor executor = new FunctionExecutor(bookService, consoleProcessor);
		this.target = new BookController(executor, consoleProcessor);
	}

	private String inputMode(final ConsoleProcessor consoleProcessor) {
		try {
			return consoleProcessor.inputNumber(OutputHandler::showSelectMode);
		} catch (IllegalArgumentException e) {
			throw BookException.of(NOT_SUPPORT_FUNCTION);
		}
	}

	@Override
	public void run() {
		target.run();
	}
}
