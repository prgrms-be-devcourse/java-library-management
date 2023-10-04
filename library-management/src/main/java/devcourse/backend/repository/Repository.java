package devcourse.backend.repository;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;

import java.util.List;
import java.util.Optional;

public interface Repository {
    List<Book> findAll();

    List<Book> findByKeyword(String keyword);

    Optional<Book> findById(long id);

    void addBook(Book book);

    void deleteById(long bookId);
}
