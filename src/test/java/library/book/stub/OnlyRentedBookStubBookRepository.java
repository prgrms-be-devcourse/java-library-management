package library.book.stub;

import java.util.List;
import java.util.Optional;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.fixture.BookFixture;

public class OnlyRentedBookStubBookRepository implements BookRepository {

	@Override
	public void save(Book book) {

	}

	@Override
	public long generateNewId() {
		return 0;
	}

	@Override
	public List<Book> findAll() {
		return null;
	}

	@Override
	public List<Book> findByTitle(String title) {
		return null;
	}

	@Override
	public Optional<Book> findById(Long id) {
		Book book = BookFixture.A.toEntity();
		book.rent();
		return Optional.of(book);
	}
}
