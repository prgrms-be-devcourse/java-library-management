package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;

import java.util.LinkedList;
import java.util.Optional;

public class FileRepository implements Repository {
    private final FileStorage FILE_STORAGE;
    private final TimeChecker TIME_CHECKER = new TimeChecker();
    public int newId;

    public FileRepository() {
        FILE_STORAGE = new FileStorage();
        newId = FILE_STORAGE.loadData();
    }

    @Override
    public void save(Book book) {
        int bookId = newId++;
        book.id = bookId;
        FILE_STORAGE.DATA.put(bookId, book);
        saveData();
    }

    @Override
    public LinkedList<Book> getAll() {
        LinkedList<Book> books = new LinkedList<>();
        FILE_STORAGE.DATA.values().forEach((book) -> {
            books.add(TIME_CHECKER.checkLoadTime(book));
        });
        saveData();
        return books;
    }

    @Override
    public LinkedList<Book> getByName(String name) {
        LinkedList<Book> books = new LinkedList<>();
        FILE_STORAGE.DATA.values().forEach(
                book -> {
                    TIME_CHECKER.checkLoadTime(book);
                    if (book.name.contains(name)) books.add(book);
                }
        );
        saveData();
        return books;
    }

    @Override
    public Book findById(int id) {
        Optional<Book> bookOpt = Optional.ofNullable(FILE_STORAGE.DATA.get(id));
        if (bookOpt.isEmpty())
            throw new BookNotFoundException();
        Book book = TIME_CHECKER.checkLoadTime(bookOpt.get());
        saveData();
        return book;
    }

    @Override
    public void delete(int id) {
        FILE_STORAGE.DATA.remove(id);
        saveData();
    }

    private void saveData() {
        FILE_STORAGE.saveFile(newId);
    }
}
