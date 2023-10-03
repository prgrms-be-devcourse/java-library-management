package library.book.infra.repository;

import static library.book.exception.ErrorCode.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.exception.BookException;

public class InMemoryBookRepository implements BookRepository {

	private static final Map<Long, Book> bookStorage = new ConcurrentHashMap<>();

	@Override
	public void save(final Book book) {
		bookStorage.put(book.getId(), book);
	}

	@Override
	public long generateNewId() {
		return bookStorage.keySet().stream()
			.max(Long::compare)
			.orElse(0L) + 1L;
	}

	@Override
	public List<Book> findAll() {
		return bookStorage.values()
			.stream()
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();
	}

	@Override
	public List<Book> findByTitle(final String title) {
		return bookStorage.values()
			.stream()
			.filter(book -> book.getTitle().contains(title))
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();
	}

	@Override
	public Optional<Book> findById(Long id) {
		return Optional.ofNullable(bookStorage.get(id));
	}

	@Override
	public void deleteById(long id) {
		bookStorage.keySet()
			.stream()
			.filter(key -> key == id)
			.findAny()
			.ifPresentOrElse(
				bookStorage::remove,
				() -> {
					throw BookException.of(NOT_FOUND);
				});
	}
}
