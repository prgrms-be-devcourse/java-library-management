package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.util.Optional;

public class FileRepository implements Repository {
    private final FileStorage fileStorage;

    public FileRepository() {
        fileStorage = new FileStorage();
    }

    @Override
    public void create(Book book) {
        int bookId = fileStorage.newId++;
        book.id = bookId;
        fileStorage.data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (fileStorage.data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        fileStorage.data.values().forEach((book) -> {
            sb.append(checkLoadTime(book));
        });
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        fileStorage.data.values().forEach(
                book -> {
                    checkLoadTime(book);
                    if (book.name.contains(bookName)) sb.append(book);
                }
        );
        if (sb.isEmpty())
            throw new EmptyLibraryException();
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> bookOpt = Optional.ofNullable(fileStorage.data.get(bookId));
        if (bookOpt.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(bookOpt.get());
    }

    @Override
    public void delete(int bookId) {
        fileStorage.data.remove(bookId);
    }

    public void save() {
        fileStorage.saveFile();
    }
}
