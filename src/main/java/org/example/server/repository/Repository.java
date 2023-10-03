package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Repository {
    default Book checkLoadTime(Book book) {
        if (BookState.valueOf(book.state).equals(BookState.LOADING) && book.endLoadTime.isPresent() && book.endLoadTime.get().isBefore(LocalDateTime.now())) {
            book.state = BookState.CAN_BORROW.name();
            book.endLoadTime = Optional.empty();
            return book;
        }
        return book;
    }

    void create(Book book);

    String readAll();

    String searchByName(String bookName);

    Book getById(int bookId);

    void delete(int bookId);

    void save();
}
