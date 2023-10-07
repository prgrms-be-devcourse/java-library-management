package repository;

import domain.Book;
import domain.BookState;
import java.util.List;

public interface Repository {

    public void register(Book book);

    public List<Book> getList();
    public List<Book> search(String titleWord);

    public BookState rental(int id);

    public BookState returnBook(int id, int time);
    public BookState lostBook(int id);
    public boolean deleteBook(int id);
    
    default public void organizeState(List<Book> books) {
        books.forEach(book -> {
            if(book.getState() == BookState.ORGANIZING) book.changeStateToAvailable();
        });
    }
}
