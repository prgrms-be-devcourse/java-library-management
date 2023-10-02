package library.book.manager;

import static library.book.exception.ErrorCode.*;

import library.book.application.BookService;
import library.book.application.DefaultBookService;
import library.book.domain.BookRepository;
import library.book.exception.BookException;
import library.book.presentation.io.InputHandler;
import library.book.presentation.io.OutputHandler;
import library.book.presentation.BookController;
import library.book.presentation.converter.InputConverter;
import library.book.presentation.io.IoProcessor;
import library.book.presentation.utils.FunctionExecutor;

public class BookClient extends BookController {

	private final BookController target;

	public BookClient(
		final InputHandler inputHandler,
		final OutputHandler outputHandler
	) {
		super(null, null);

		InputConverter converter = new InputConverter();

		IoProcessor ioProcessor = new IoProcessor(inputHandler, outputHandler, converter);

		String mode = inputMode(ioProcessor);

		BookRepository bookRepository = ModeManager.valueOf(mode).getRepository();
		BookService bookService = new DefaultBookService(bookRepository);

		FunctionExecutor executor = new FunctionExecutor(bookService, ioProcessor);
		this.target = new BookController(executor, ioProcessor);
	}

	private String inputMode(final IoProcessor ioProcessor) {
		try {
			return ioProcessor.inputNumber(OutputHandler::showSelectMode);
		} catch (IllegalArgumentException e) {
			throw BookException.of(NOT_SUPPORT_FUNCTION);
		}
	}

	@Override
	public void run() {
		target.run();
	}
}
