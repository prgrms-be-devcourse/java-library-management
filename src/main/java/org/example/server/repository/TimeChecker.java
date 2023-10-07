package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;

import java.time.LocalDateTime;

public class TimeChecker {
    Book checkLoadTime(Book book) {
        if (BookStatusType.valueOf(book.status.getType().name()).equals(BookStatusType.LOAD)) {
            LoadStatus bookStatus = (LoadStatus) book.status;
            if (bookStatus.endLoadTime.isBefore(LocalDateTime.now())) {
                book.status = BookStatusType.CAN_BORROW.getBookStatus();
                return book;
            }
        }
        return book;
    }
}
