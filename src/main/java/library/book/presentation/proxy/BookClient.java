package library.book.presentation.proxy;

import library.book.application.BookService;
import library.book.domain.BookRepository;
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
		final OutputHandler outputHandler,
		final InputConverter converter
	) {
		super(null, null);

		ConsoleProcessor consoleProcessor = new ConsoleProcessor(inputHandler, outputHandler, converter);
		String mode = consoleProcessor.inputNumber(OutputHandler::showSelectMode);

		BookRepository bookRepository = ModeManager.valueOf(mode).getRepository();
		BookService bookService = ModeManager.valueOf(mode).getService(bookRepository);

		FunctionExecutor executor = new FunctionExecutor(bookService, consoleProcessor);
		this.target = new BookController(executor, consoleProcessor);
	}

	@Override
	public void run() {
		target.run();
	}
}
