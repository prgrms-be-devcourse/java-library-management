package org.example.server.entity.bookStatus;

import org.example.server.exception.LoadingException;

import java.time.LocalDateTime;

public class LoadStatus implements BookStatus {
    BookStatusType bookStatusType = BookStatusType.LOAD;
    LocalDateTime endLoadTime;

    public LoadStatus() {
        endLoadTime = LocalDateTime.now().plusMinutes(5);
    }

    public LoadStatus(String endLoadTime) {
        this.endLoadTime = LocalDateTime.parse(endLoadTime);
    }

    @Override
    public BookStatusType getBookStatusType() {
        return bookStatusType;
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

    public LocalDateTime getEndLoadTime() {
        return endLoadTime;
    }
}
