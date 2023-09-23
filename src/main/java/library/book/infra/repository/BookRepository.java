package library.book.infra.repository;

import library.book.domain.Book;

public interface BookRepository {

	void save(Book book);

	long generateNewId();
}
