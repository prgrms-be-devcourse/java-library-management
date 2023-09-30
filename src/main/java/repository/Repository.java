package repository;

import domain.BookState;

import java.io.*;
import java.util.List;

public interface Repository {

    public void register(Book book) throws IOException;

    public void printList();
    public void search(String titleWord);

    public void rental(int id) throws IOException;

    public void returnBook(int id) throws IOException;
    public void lostBook(int id) throws IOException;
    public void deleteBook(int id) throws IOException;
    
    default public void organizeState(List<Book> books) {
        books.forEach(book -> {
            if(book.getState() == BookState.ORGANIZING) book.setState(BookState.AVAILABLE);
        });
    }
}
