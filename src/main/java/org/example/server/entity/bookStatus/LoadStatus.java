package org.example.server.entity.bookStatus;

import org.example.server.exception.LoadingException;

import java.time.LocalDateTime;

public class LoadStatus implements BookStatus {
    public final LocalDateTime END_LOAD_TIME;
    private final BookStatusType TYPE = BookStatusType.LOAD;

    public LoadStatus() {
        END_LOAD_TIME = LocalDateTime.now().plusMinutes(5);
    }

    public LoadStatus(String endLoadTime) {
        this.END_LOAD_TIME = LocalDateTime.parse(endLoadTime);
    }

    @Override
    public BookStatusType getType() {
        return TYPE;
    }

    @Override
    public void borrow() {
        throw new LoadingException(System.lineSeparator() + "[System] 해당 도서는 정리중입니다." + System.lineSeparator());
    }

    @Override
    public void restore() {
        throw new LoadingException(System.lineSeparator() + "[System] 해당 도서는 이미 반납되어, 도서 정리중입니다." + System.lineSeparator());
    }

    @Override
    public void lost() {
        // 성공
    }
}
