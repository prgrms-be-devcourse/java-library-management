package org.example.server.entity.bookStatus;

public interface BookStatus {
    BookStatusType getType();

    void checkCanBorrow();

    void checkCanRestore();

    void checkCanLost();
}
