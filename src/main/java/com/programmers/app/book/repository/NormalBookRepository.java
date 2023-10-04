package com.programmers.app.book.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.file.FileManager;

public class NormalBookRepository implements BookRepository {

    private final FileManager<HashMap<Integer, Book>, List<Book>> fileManager;
    private Map<Integer, Book> books;

    public NormalBookRepository(FileManager<HashMap<Integer, Book>, List<Book>> fileManager) throws IOException {
        this.fileManager = fileManager;
        this.books = fileManager.loadDataFromFile();
    }

    @Override
    public void add(Book book) {
        books.put(book.getBookNumber(), book);
    }

    @Override
    public int getLastBookNumber() {
        if (books.isEmpty()) return 0;
        return Collections.max(books.keySet());
    }

    @Override
    public Optional<List<Book>> findAllBooks() {
        if (books.isEmpty()) return Optional.empty();

        List<Book> bookList = new ArrayList<>(books.values());
        bookList.sort(Comparator.comparingInt(Book::getBookNumber));
        bookList = bookList
                .stream()
                .map(this::updateBookIfArranged)
                .collect(Collectors.toList());
        return Optional.of(bookList);
    }

    @Override
    public Optional<List<Book>> findByTitle(String title) {
        List<Book> foundBooks = books.values()
                .stream()
                .filter(book -> book.containsInTitle(title))
                .map(this::updateBookIfArranged)
                .collect(Collectors.toList());

        if (foundBooks.isEmpty()) return Optional.empty();

        foundBooks.forEach(this::updateBookIfArranged);
        return Optional.of(foundBooks);
    }

    @Override
    public Optional<Book> findByBookNumber(int bookNumber) {
        return Optional.ofNullable(books.get(bookNumber))
                .map(this::updateBookIfArranged);
    }

    @Override
    public void delete(Book book) {
        books.remove(book.getBookNumber());
    }

    @Override
    public Book updateBookIfArranged(Book book) {
        if (book.isDoneArranging()) {
            updateBookStatus(book, BookStatus.IN_PLACE);
            save();
        }

        return books.get(book.getBookNumber());
    }

    @Override
    public void updateBookStatus(Book book, BookStatus bookStatus) {
        if (bookStatus == BookStatus.ON_ARRANGEMENT) {
            books.put(book.getBookNumber(), book.generateReturnedBook());
        }

        else {
            books.put(book.getBookNumber(), book.generateStatusUpdatedBook(bookStatus));
        }
    }

    @Override
    public void save() {
        fileManager.save(new ArrayList<>(books.values()));
    }
}
