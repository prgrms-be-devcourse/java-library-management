package repository;

import model.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {

    void saveBook(Book book);
    List<Book> findAllBook();
    List<Book> findBookByTitle(String title);
    void deleteBook(Long bookNo);
    Long createBookNo();
    Optional<Book> findBookByBookNo(Long bookNo);
}