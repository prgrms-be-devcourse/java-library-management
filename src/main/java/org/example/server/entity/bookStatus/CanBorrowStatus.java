package org.example.server.entity.bookStatus;

import org.example.server.exception.AlreadyCanBorrowException;

public class CanBorrowStatus implements BookStatus {
    private final BookStatusType type = BookStatusType.CAN_BORROW;

    @Override
    public BookStatusType getType() {
        return type;
    }

    @Override
    public void checkCanBorrow() {
        // 성공
    }

    @Override
    public void checkCanRestore() {
        throw new AlreadyCanBorrowException();
    }

    @Override
    public void checkCanLost() {
        // 성공
    }
}
