package library.book.presentation.manager;

import java.util.function.BiConsumer;

import library.book.application.BookManageService;
import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.exception.ErrorCode;
import library.book.exception.LibraryException;

public enum FunctionManger {

	ONE((bookService, request) -> {
		BookManageService bookManageService = getValidatedBookManageService(bookService);
		RegisterBookRequest bookRequest = getValidatedRegisterBookRequest(request);

		bookManageService.registerBook(bookRequest);
	});

	FunctionManger(BiConsumer<BookService, Object> executor) {
		this.executor = executor;
	}

	private final BiConsumer<BookService, Object> executor;

	public void execute(BookService bookService, Object request) {
		this.executor.accept(bookService, request);
	}

	//== 검증 메소드 ==//
	private static BookManageService getValidatedBookManageService(BookService bookService) {
		if (!(bookService instanceof BookManageService bookManageService))
			throw LibraryException.of(ErrorCode.TYPE_MISS_MATCH);

		return bookManageService;
	}

	private static RegisterBookRequest getValidatedRegisterBookRequest(Object request) {
		if (!(request instanceof RegisterBookRequest bookRequest))
			throw LibraryException.of(ErrorCode.TYPE_MISS_MATCH);

		return bookRequest;
	}
}
