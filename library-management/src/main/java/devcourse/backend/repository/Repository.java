package devcourse.backend.repository;

import devcourse.backend.model.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {
    List<Book> findAll();

    List<Book> findByKeyword(String keyword);

    Optional<Book> findById(long id);
    Optional<Book> findByTitleAndAuthor(String title, String author);

    void addBook(Book book);

    void deleteById(long bookId);

    void flush();
}
