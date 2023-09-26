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
	public void registerBook(final RegisterBookRequest request) {
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
		return bookRepository.findAll()
			.stream()
			.map(book ->
				new BookSearchResponse(
					book.getId(),
					book.getTitle(),
					book.getAuthorName(),
					book.getPages(),
					book.getBookStatus().getDescription()
				)).toList();
	}

	@Override
	public List<BookSearchResponse> searchBooks(final String title) {
		return bookRepository.findByTitle(title)
			.stream()
			.map(book ->
				new BookSearchResponse(
					book.getId(),
					book.getTitle(),
					book.getAuthorName(),
					book.getPages(),
					book.getBookStatus().getDescription()
				)).toList();
	}

	@Override
	public void rentBook(long id) {
		Book book = bookRepository.getById(id);

		book.rent();
	}

	@Override
	public void returnBook(long id) {
		Book book = bookRepository.getById(id);

		book.returnBook();
	}

	@Override
	public void registerAsLost(long id) {
		Book book = bookRepository.getById(id);

		book.registerAsLost();
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}
}
