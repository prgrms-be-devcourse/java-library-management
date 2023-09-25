package library.book.domain;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

	void save(Book book);

	long generateNewId();

	List<Book> findAll();

	List<Book> findByTitle(final String title);

	Optional<Book> findById(final Long id);
}
