package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

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
		consoleProcessor.outputBookInfo(
			responses,
			ENTRY_SEARCH_ALL_BOOKS.getValue(),
			COMPLETE_SEARCH_ALL_BOOKS.getValue()
		);
	}

	public void executeSearchBooksByTitle() {
		String title = consoleProcessor.inputString();

		List<BookSearchResponse> responses = bookService.searchBooks(title);
		consoleProcessor.outputBookInfo(
			responses,
			ENTRY_SEARCH_BOOKS_BY_TITLE.getValue(),
			""
		);
	}

	public void executeRentBook() {
		long id = consoleProcessor.inputBookId(ENTRY_RENT_BOOK, INPUT_RENT_BOOK_ID);

		bookService.rentBook(id);
		consoleProcessor.outputCompleteMessage(COMPLETE_RENT);
	}

	public void executeReturnBook() {
		long id = consoleProcessor.inputBookId(ENTRY_RETURN_BOOK, INPUT_RETURN_BOOK_ID);

		bookService.returnBook(id);
		consoleProcessor.outputCompleteMessage(COMPLETE_RETURN);
	}

	public void executeRegisterAsLost() {
		long id = consoleProcessor.inputBookId(ENTRY_LOST_BOOK, INPUT_LOST_BOOK_ID);

		bookService.registerAsLost(id);
		consoleProcessor.outputCompleteMessage(COMPLETE_LOST);
	}

	public void executeDeleteBook() {
		long id = consoleProcessor.inputBookId(ENTRY_DELETE, INPUT_LOST_BOOK_ID);

		bookService.deleteBook(id);
		consoleProcessor.outputCompleteMessage(COMPLETE_DELETE);
	}
}
