package org.example.server.entity.bookStatus;

import org.example.server.exception.AlreadyBorrowedException;

public class BorrowedStatus implements BookStatus {
    private final BookStatusType TYPE = BookStatusType.BORROWED;

    @Override
    public BookStatusType getType() {
        return TYPE;
    }

    @Override
    public void borrow() {
        throw new AlreadyBorrowedException();
    }

    @Override
    public void restore() {
        // 성공
    }

    @Override
    public void lost() {
        // 성공
    }
}
