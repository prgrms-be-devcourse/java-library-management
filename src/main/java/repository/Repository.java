package repository;

import domain.Book;

import java.util.List;
import java.util.Optional;

public interface Repository {

    public void addBook(Book book);
    public void deleteBook(Book book);
    public Optional<Book> findById(Long bookNo);
    public List<Book> findByTitle(String title);
    public List<Book> bookList();

    void clear();
}
