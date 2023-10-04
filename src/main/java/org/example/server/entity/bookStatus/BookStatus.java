package org.example.server.entity.bookStatus;

public interface BookStatus {
    BookStatusType getType();

    void borrow();

    void restore();

    void lost();
}
