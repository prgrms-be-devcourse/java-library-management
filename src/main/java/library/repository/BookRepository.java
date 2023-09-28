package library.repository;


import library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void add(Book item);

    Optional<Book> findByBookNumber(long bookNumber);

    List<Book> findAll();

    List<Book> findListContainTitle(String title);

    void delete(Book book);

    long getNextBookNumber();
}
