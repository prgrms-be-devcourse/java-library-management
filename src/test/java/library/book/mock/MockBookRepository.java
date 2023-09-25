package library.book.mock;

import java.util.List;

import library.book.domain.Book;
import library.book.domain.BookRepository;

public class MockBookRepository implements BookRepository {

	@Override
	public void save(Book book) {

	}

	@Override
	public long generateNewId() {
		return 1L;
	}

	@Override
	public List<Book> findAll() {
		return null;
	}
}
