package devcourse.backend.repository;

import devcourse.backend.medel.Book;

import java.util.List;

public interface Repository {
    List<Book> findAll();
    List<Book> findByKeyword(String keyword);
    Book findById(long id);
    Book findByTitleAndAuthorAndTotalPages(String title, String author, int totalPages);
    void addBook(Book book);
    void modify();
    void deleteById(long bookId);
}
