package manage.file;

import domain.Book;

import java.util.HashMap;

public interface BookFileManager {
    HashMap<Integer, Book> read();
    void write(HashMap<Integer, Book> bookList);
}
