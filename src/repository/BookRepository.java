package repository;

import domain.Book;
import domain.BookState;

import java.util.List;

public interface BookRepository {
    public List<Book> getAllBooks();

    public void addBook(String Book);

    public Book searchBookByTitle(String title);

    public void changeBookState(int id, BookState bookState);

    public void deleteBook(int id);
}
