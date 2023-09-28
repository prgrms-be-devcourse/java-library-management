package manage.book;

import entity.Book;
import entity.State;

import java.util.List;

public interface BookManager {
    Book register(Book book);
    List<Book> searchAll();
    List<Book> search(String text);
    State rent(int bookNum);
    State returnBook(int bookNum);
    State lost(int bookNum);
    State delete(int bookNum);
    void saveFile();
}
