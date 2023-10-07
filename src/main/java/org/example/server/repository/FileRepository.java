package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;

import java.util.LinkedList;
import java.util.Optional;

public class FileRepository implements Repository {
    private final FileStorage fileStorage;
    private final TimeChecker timeChecker = new TimeChecker();
    public int newId;

    public FileRepository() {
        fileStorage = new FileStorage();
        newId = fileStorage.loadData();
    }

    @Override
    public void save(Book book) {
        int bookId = newId++;
        book.id = bookId;
        fileStorage.data.put(bookId, book);
        saveData();
    }

    @Override
    public LinkedList<Book> findAll() {
        LinkedList<Book> books = new LinkedList<>();
        fileStorage.data.values().forEach((book) -> {
            books.add(timeChecker.checkLoadTime(book));
        });
        saveData();
        return books;
    }

    @Override
    public LinkedList<Book> findAllByName(String name) {
        LinkedList<Book> books = new LinkedList<>();
        fileStorage.data.values().forEach(
                book -> {
                    timeChecker.checkLoadTime(book);
                    if (book.name.contains(name)) books.add(book);
                }
        );
        saveData();
        return books;
    }

    @Override
    public Book getById(int id) {
        Optional<Book> bookOpt = Optional.ofNullable(fileStorage.data.get(id));
        if (bookOpt.isEmpty())
            throw new BookNotFoundException();
        Book book = timeChecker.checkLoadTime(bookOpt.get());
        saveData();
        return book;
    }

    @Override
    public void delete(int id) {
        fileStorage.data.remove(id);
        saveData();
    }

    private void saveData() {
        fileStorage.saveFile(newId);
    }
}
