package org.example.server.entity.bookStatus;

public interface BookStatus {
    BookStatusType getBookStatusType();

    void borrow();

    void restore();

    void lost();
}
