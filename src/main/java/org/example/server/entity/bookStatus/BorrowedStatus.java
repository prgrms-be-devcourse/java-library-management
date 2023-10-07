package org.example.server.entity.bookStatus;

import org.example.server.exception.AlreadyBorrowedException;

public class BorrowedStatus implements BookStatus {
    private final BookStatusType type = BookStatusType.BORROWED;

    @Override
    public BookStatusType getType() {
        return type;
    }

    @Override
    public void checkCanBorrow() {
        throw new AlreadyBorrowedException();
    }

    @Override
    public void checkCanRestore() {
        // 성공
    }

    @Override
    public void checkCanLost() {
        // 성공
    }
}
