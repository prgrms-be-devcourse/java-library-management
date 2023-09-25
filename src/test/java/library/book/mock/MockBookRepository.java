package library.book.mock;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.fixture.BookFixture;

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
		return Arrays.stream(BookFixture.values())
			.map(BookFixture::toEntity)
			.sorted(Comparator.comparingLong(Book::getId))
			.toList();
	}
}
