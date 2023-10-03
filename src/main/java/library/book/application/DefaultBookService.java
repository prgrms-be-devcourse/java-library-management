package library.book.application;

import static library.book.domain.state.Cleaning.*;

import java.util.List;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.application.dto.response.BookSearchResponse;
import library.book.application.utils.BookScheduler;
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
					book.getBookState().getDescription()
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
					book.getBookState().getDescription()
				)).toList();
	}

	@Override
	public void rentBook(long id) {
		Book book = bookRepository.getById(id);
		book.rent();

		bookRepository.save(book);
	}

	@Override
	public void returnBook(long id) {
		Book book = bookRepository.getById(id);
		book.returnBook();

		bookRepository.save(book);

		registerFinishCleaningTask(id);
	}

	@Override
	public void registerAsLost(long id) {
		Book book = bookRepository.getById(id);
		book.registerAsLost();

		bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);
	}

	private void registerFinishCleaningTask(long id) {
		Runnable finishCleaningTask = () ->
			bookRepository.findById(id)
				.ifPresent(findBook -> {
					findBook.finishCleaning();
					bookRepository.save(findBook);
				});

		BookScheduler.registerTask(finishCleaningTask, CLEANING_END_AT);
	}
}
