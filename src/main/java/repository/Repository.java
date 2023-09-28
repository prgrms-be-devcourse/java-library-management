package repository;

import domain.Books;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface Repository {

    public void addBook(Books book);
    public void deleteBook(Books book);
    public Optional<Books> findById(Long bookNo);
    public List<Books> findByTitle(String title);
    public List<Books> bookList();
}
