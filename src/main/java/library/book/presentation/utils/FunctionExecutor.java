package library.book.presentation.utils;

import static library.book.presentation.constant.Message.*;

import java.util.List;

import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.presentation.io.IoProcessor;

public class FunctionExecutor {

	private final BookService bookService;
	private final IoProcessor ioProcessor;

	public FunctionExecutor(
		final BookService bookService,
		final IoProcessor ioProcessor
	) {
		this.bookService = bookService;
		this.ioProcessor = ioProcessor;
	}

	public void executeRegisterBook() {
		RegisterBookRequest request = ioProcessor.inputBookInfo();

		bookService.registerBook(request);
	}

	public void executeSearchAllBooks() {
		List<BookSearchResponse> responses = bookService.searchBooks();
		ioProcessor.outputBookInfo(
			responses,
			ENTRY_SEARCH_ALL_BOOKS.getValue(),
			COMPLETE_SEARCH_ALL_BOOKS.getValue()
		);
	}

	public void executeSearchBooksByTitle() {
		String title = ioProcessor.inputString();

		List<BookSearchResponse> responses = bookService.searchBooks(title);
		ioProcessor.outputBookInfo(
			responses,
			ENTRY_SEARCH_BOOKS_BY_TITLE.getValue(),
			COMPLETE_SEARCH_ALL_BOOKS.getValue()
		);
	}

	public void executeRentBook() {
		long id = ioProcessor.inputBookId(ENTRY_RENT_BOOK, INPUT_RENT_BOOK_ID);

		bookService.rentBook(id);
		ioProcessor.outputCompleteMessage(COMPLETE_RENT);
	}

	public void executeReturnBook() {
		long id = ioProcessor.inputBookId(ENTRY_RETURN_BOOK, INPUT_RETURN_BOOK_ID);

		bookService.returnBook(id);
		ioProcessor.outputCompleteMessage(COMPLETE_RETURN);
	}

	public void executeRegisterAsLost() {
		long id = ioProcessor.inputBookId(ENTRY_LOST_BOOK, INPUT_LOST_BOOK_ID);

		bookService.registerAsLost(id);
		ioProcessor.outputCompleteMessage(COMPLETE_LOST);
	}

	public void executeDeleteBook() {
		long id = ioProcessor.inputBookId(ENTRY_DELETE, INPUT_LOST_BOOK_ID);

		bookService.deleteBook(id);
		ioProcessor.outputCompleteMessage(COMPLETE_DELETE);
	}
}
