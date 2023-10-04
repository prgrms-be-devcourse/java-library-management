package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;

import java.time.LocalDateTime;

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

    void create(Book book);

    String readAll();

    String searchByName(String bookName);

    Book getById(int bookId);

    void delete(int bookId);

    void save();
}
