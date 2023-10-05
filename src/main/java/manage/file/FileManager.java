package manage.file;

import domain.Book;

import java.util.HashMap;

public interface FileManager {
    HashMap<Integer, Book> read();
    void write(HashMap<Integer, Book> bookList);
}
