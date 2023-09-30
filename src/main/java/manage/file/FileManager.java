package manage.file;

import entity.Book;

import java.util.HashMap;

public interface FileManager {
    HashMap<Integer, Book> read();
    void write(HashMap<Integer, Book> bookList);
}
