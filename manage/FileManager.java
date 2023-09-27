package manage;

import entity.Book;

import java.util.List;

public interface FileManager {
    List<Book> readCsv(String filePath);
    void writeCsv(List<Book> bookList);
}
