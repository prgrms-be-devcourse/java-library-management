package library.book.domain;

import static library.book.exception.ErrorCode.*;

import java.util.List;
import java.util.Optional;

import library.book.exception.BookException;

public interface BookRepository {

	void save(Book book);

	long generateNewId();

	List<Book> findAll();

	List<Book> findByTitle(final String title);

	Optional<Book> findById(final Long id);

	default Book getById(final Long id) {
		return findById(id)
			.orElseThrow(() -> BookException.of(NOT_FOUND));
	}
}
