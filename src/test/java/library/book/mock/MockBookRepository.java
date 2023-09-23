package library.book.mock;

import library.book.domain.Book;
import library.book.infra.repository.BookRepository;

public class MockBookRepository implements BookRepository {

	@Override
	public void save(Book book) {

	}

	@Override
	public long generateNewId() {
		return 1L;
	}
}
