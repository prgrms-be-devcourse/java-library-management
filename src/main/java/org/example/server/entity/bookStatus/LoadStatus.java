package org.example.server.entity.bookStatus;

import org.example.server.exception.LoadingException;

import java.time.LocalDateTime;

public class LoadStatus implements BookStatus {
    public final LocalDateTime endLoadTime;
    private final BookStatusType type = BookStatusType.LOAD;

    public LoadStatus() {
        endLoadTime = LocalDateTime.now().plusMinutes(5);
    }

    public LoadStatus(String endLoadTime) {
        this.endLoadTime = LocalDateTime.parse(endLoadTime);
    }

    @Override
    public BookStatusType getType() {
        return type;
    }

    @Override
    public void checkCanBorrow() {
        throw new LoadingException(System.lineSeparator() + "[System] 해당 도서는 정리중입니다." + System.lineSeparator());
    }

    @Override
    public void checkCanRestore() {
        throw new LoadingException(System.lineSeparator() + "[System] 해당 도서는 이미 반납되어, 도서 정리중입니다." + System.lineSeparator());
    }

    @Override
    public void checkCanLost() {
        // 성공
    }
}
