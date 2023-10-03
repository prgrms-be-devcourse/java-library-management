package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;

import java.text.ParseException;
import java.util.Date;

public interface Repository {
    default Book checkLoadTime(Book book) {
        if (BookState.valueOf(book.state).equals(BookState.LOADING)) {
            try {
                Date endLoadTime = Book.format.parse(book.endLoadTime);
                Date now = Book.format.parse(Book.format.format(new Date()));
                if (now.after(endLoadTime)) {
                    book.state = BookState.CAN_BORROW.name();
                    book.endLoadTime = "";
                    return book;
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
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
