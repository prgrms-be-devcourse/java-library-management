package library.book.application.proxy;

import java.util.List;

import library.book.application.BookService;
import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.domain.BookRepository;

public class DataSaver implements BookService {

	private final BookService target;
	private final BookRepository bookRepository;

	public DataSaver(
		final BookService target,
		final BookRepository bookRepository
	) {
		this.target = target;
		this.bookRepository = bookRepository;
	}

	@Override
	public void registerBook(final RegisterBookRequest request) {
		target.registerBook(request);
		bookRepository.updateData();
	}

	@Override
	public List<BookSearchResponse> searchBooks() {
		List<BookSearchResponse> result = target.searchBooks();
		bookRepository.updateData();
		return result;
	}

	@Override
	public List<BookSearchResponse> searchBooks(final String title) {
		List<BookSearchResponse> result = target.searchBooks(title);
		bookRepository.updateData();
		return result;
	}

	@Override
	public void rentBook(final long id) {
		target.rentBook(id);
		bookRepository.updateData();
	}

	@Override
	public void returnBook(final long id) {
		target.returnBook(id);
		bookRepository.updateData();
	}

	@Override
	public void registerAsLost(final long id) {
		target.registerAsLost(id);
		bookRepository.updateData();
	}

	@Override
	public void deleteBook(final Long id) {
		target.deleteBook(id);
		bookRepository.updateData();
	}
}
