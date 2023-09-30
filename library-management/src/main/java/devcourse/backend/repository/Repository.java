package devcourse.backend.repository;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;

import java.util.List;

public interface Repository {
    List<Book> findAll();

    List<Book> findByKeyword(String keyword);

    Book findById(long id);

    void addBook(Book book);

    void changeStatus(long id, BookStatus status);

    void deleteById(long bookId);
}
