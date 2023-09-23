package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.domain.Book;
import library.book.infra.repository.BookRepository;

public class IoBookService implements BookService {

	private final BookRepository bookRepository;

	public IoBookService(final BookRepository bookRepository) {
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
