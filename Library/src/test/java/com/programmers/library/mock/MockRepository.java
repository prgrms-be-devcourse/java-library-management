package com.programmers.library.mock;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;
import com.programmers.library.repository.Repository;

import java.util.*;

public class MockRepository implements Repository {
    private Map<Long, Book> bookMap = new HashMap<>();
    private Long lastId = 0L;

    @Override
    public void register(Book book) {
        Long bookId = ++lastId;
        bookMap.put(bookId, book);
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public void deleteBook(Long id) {
        bookMap.remove(id);
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getTitle().contains(title)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return Optional.ofNullable(bookMap.get(id));
    }

    @Override
    public void updateStatus(Book book, BookStatus bookStatus) {
        book.updateBookStatus(bookStatus);
    }

    @Override
    public Long findLastId() {
        return lastId;
    }

}
