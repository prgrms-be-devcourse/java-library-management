package library.book.domain;

import java.util.List;

public interface BookRepository {

	void save(Book book);

	long generateNewId();

	List<Book> findAll();

	List<Book> findByTitle(final String title);
}
