package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;

public interface Repository {
    default Book checkLoadTime(Book book) {
        if (BookStatusType.valueOf(book.status.getBookStatusType().name()).equals(BookStatusType.LOAD)) {
            LoadStatus bookStatus = (LoadStatus) book.status;
            if (bookStatus.getEndLoadTime().isBefore(LocalDateTime.now())) {
                book.status = BookStatusType.CAN_BORROW.getBookStatus();
                return book;
            }
        }
        return book;
    }

    void save(Book book);

    LinkedList<Book> getAll();

    LinkedList<Book> getByName(String name);

    Book findById(int id);

    void delete(int id);
}
