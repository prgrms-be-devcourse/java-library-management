package manage.book;

import domain.Book;
import domain.BookProcess;
import domain.BookState;

import java.util.List;

public interface BookManager {
    Book register(Book book);
    List<Book> searchAll();
    List<Book> search(String text);
    BookState process(int bookNum, BookProcess bookProcess);
    void saveFile();
}
