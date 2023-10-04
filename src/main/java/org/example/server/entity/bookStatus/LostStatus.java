package org.example.server.entity.bookStatus;

import org.example.server.exception.LostException;

public class LostStatus implements BookStatus {
    private final BookStatusType TYPE = BookStatusType.LOST;

    @Override
    public BookStatusType getType() {
        return TYPE;
    }

    @Override
    public void borrow() {
        throw new LostException();
    }

    @Override
    public void restore() {
        // 성공
    }

    @Override
    public void lost() {
        throw new LostException();
    }
}
