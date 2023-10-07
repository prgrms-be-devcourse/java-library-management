package org.example.server.entity.bookStatus;

import org.example.server.exception.LostException;

public class LostStatus implements BookStatus {
    private final BookStatusType type = BookStatusType.LOST;

    @Override
    public BookStatusType getType() {
        return type;
    }

    @Override
    public void checkCanBorrow() {
        throw new LostException();
    }

    @Override
    public void checkCanRestore() {
        // 성공
    }

    @Override
    public void checkCanLost() {
        throw new LostException();
    }
}
