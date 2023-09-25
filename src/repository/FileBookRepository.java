package repository;

import domain.Book;
import domain.BookState;

import java.util.List;

public class FileBookRepository implements BookRepository {
    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public void addBook(String Book) {

    }

    @Override
    public Book searchBookByTitle(String title) {
        return null;
    }

    @Override
    public void changeBookState(int id, BookState bookState) {

    }

    @Override
    public void deleteBook(int id) {
        
    }
}
