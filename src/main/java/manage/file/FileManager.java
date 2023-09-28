package manage.file;

import entity.Book;

import java.util.List;

public interface FileManager {
    List<Book> read();
    void write(List<Book> bookList);
}
