package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.*;

public class MemBookRepository implements BookRepository {
    private Map<Integer, Book> books = new HashMap<>();

    private MemBookRepository() {
    }

    private static class Holder {
        private static final MemBookRepository INSTANCE = new MemBookRepository();
    }

    public static MemBookRepository getInstance() {
        return MemBookRepository.Holder.INSTANCE;
    }

    @Override
    public void addBook(Book book) {
        book.setId(createUniqueId());
        books.put(book.getId(), book);

        System.out.println(books.get(book.getId()).toString());
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<Book>(books.values());
    }


    @Override
    public Optional<Book> findBookById(int id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().contains(title))
                .findAny();
    }

    @Override
    public void updateBookState(int id, BookState bookState) {
        books.get(id).setState(bookState);
    }

    @Override
    public void deleteBook(int id) {
        books.remove(id);
    }

    @Override
    public int createUniqueId() {
        int randomId;
        Random random = new Random();
        do {
            randomId = random.nextInt(Integer.MAX_VALUE);
        } while (this.books.containsKey(randomId));
        return randomId;
    }
}
