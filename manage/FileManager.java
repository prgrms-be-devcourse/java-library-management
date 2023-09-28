package manage;

import entity.Book;

import java.util.List;

public interface FileManager {
    List<Book> read(String filePath);
    void write(List<Book> bookList);
}
