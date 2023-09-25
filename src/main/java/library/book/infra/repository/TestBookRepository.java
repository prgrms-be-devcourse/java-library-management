package library.book.infra.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import library.book.domain.Book;
import library.book.domain.BookRepository;

public class TestBookRepository implements BookRepository {

	private static final Map<Long, Book> bookStorage = new ConcurrentHashMap<>();

	@Override
	public void save(Book book) {
		bookStorage.put(book.getId(), book);
	}

	@Override
	public long generateNewId() {
		return bookStorage.keySet().stream()
			.max(Long::compare)
			.orElse(0L) + 1L;
	}
}
