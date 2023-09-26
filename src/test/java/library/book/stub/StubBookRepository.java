package library.book.stub;

import static library.book.fixture.BookFixture.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import library.book.domain.Book;
import library.book.domain.BookRepository;
import library.book.fixture.BookFixture;

public class StubBookRepository implements BookRepository {

	@Override
	public void save(Book book) {
		System.out.println("[call save]");
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

	@Override
	public List<Book> findByTitle(final String title) {
		return List.of(B.toEntity());
	}

	@Override
	public Optional<Book> findById(Long id) {
		return Optional.of(A.toEntity());
	}

	@Override
	public void deleteById(long id) {
		System.out.println("[call deleteById]");
	}
}
