package library.book.domain;

public interface BookRepository {

	void save(Book book);

	long generateNewId();
}
