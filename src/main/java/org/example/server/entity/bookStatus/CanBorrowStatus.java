package org.example.server.entity.bookStatus;

import org.example.server.exception.AlreadyCanBorrowException;

public class CanBorrowStatus implements BookStatus {
    BookStatusType bookStatusType = BookStatusType.CAN_BORROW;

    @Override
    public BookStatusType getBookStatusType() {
        return bookStatusType;
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
