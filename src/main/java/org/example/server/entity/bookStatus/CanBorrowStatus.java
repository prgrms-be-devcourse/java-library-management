package org.example.server.entity.bookStatus;

import org.example.server.exception.AlreadyCanBorrowException;

public class CanBorrowStatus implements BookStatus {
    private final BookStatusType TYPE = BookStatusType.CAN_BORROW;

    @Override
    public BookStatusType getType() {
        return TYPE;
    }

    @Override
    public void borrow() {
        // 성공
    }

    @Override
    public void restore() {
        throw new AlreadyCanBorrowException();
    }

    @Override
    public void lost() {
        // 성공
    }
}
