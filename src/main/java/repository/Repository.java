package repository;

import domain.BookState;

import java.io.*;
import java.util.List;

public interface Repository {

    public void register(Book book);

    public void printList();
    public void search(String titleWord);

    public void rental(int id);

    public void returnBook(int id);
    public void lostBook(int id);
    public void deleteBook(int id);
    
    default public void organizeState(List<Book> books) {
        books.forEach(book -> {
            if(book.getState() == BookState.ORGANIZING) book.setState(BookState.AVAILABLE);
        });
    }
}
