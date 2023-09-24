package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.domain.Book;
import library.book.domain.BookRepository;

public class IoBookManageService implements BookManageService {

	private final BookRepository bookRepository;

	public IoBookManageService(final BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void registerBook(RegisterBookRequest request) {
		long newId = bookRepository.generateNewId();

		Book book = Book.createBook(
			newId,
			request.title(),
			request.authorName(),
			request.pages()
		);

		bookRepository.save(book);
	}
}
