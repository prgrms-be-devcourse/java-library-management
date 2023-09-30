package manage.book;

import entity.Book;
import entity.BookState;

import java.util.List;

public interface BookManager {
    Book register(Book book);
    List<Book> searchAll();
    List<Book> search(String text);
    BookState rent(int bookNum);
    BookState revert(int bookNum);
    BookState lost(int bookNum);
    BookState delete(int bookNum);
    void saveFile();
}
