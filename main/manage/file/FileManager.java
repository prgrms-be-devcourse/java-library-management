package main.manage.file;

import main.entity.Book;

import java.util.List;

public interface FileManager {
    List<Book> read();
    void write(List<Book> bookList);
}
