package library.book.application;

import java.util.List;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.domain.Book;
import library.book.domain.BookRepository;

public class DefaultBookService implements BookService {

	private final BookRepository bookRepository;

	public DefaultBookService(final BookRepository bookRepository) {
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

	@Override
	public List<BookSearchResponse> searchBooks() {
		return null;
	}
}
