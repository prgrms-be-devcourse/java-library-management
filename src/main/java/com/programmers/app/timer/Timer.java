package com.programmers.app.timer;

import static com.programmers.app.config.AppConfig.MINUTES_FOR_BOOK_ARRANGEMENT;

import java.time.LocalDateTime;

import com.programmers.app.timer.dto.TimerJson;

public class Timer {

    private long bookNumber;
    private LocalDateTime arrangementBegunAt;

    public Timer (long bookNumber, LocalDateTime arrangementBegunAt) {
        this.bookNumber = bookNumber;
        this.arrangementBegunAt = arrangementBegunAt;
    }

    public long getBookNumber() {
        return bookNumber;
    }

    public boolean isCompleted(LocalDateTime localDateTime) {
        LocalDateTime completedAt = arrangementBegunAt.plusMinutes(MINUTES_FOR_BOOK_ARRANGEMENT);
        return completedAt.isBefore(localDateTime);
    }

    public boolean isMatching(long bookNumber) {
        return this.bookNumber == bookNumber;
    }

    public TimerJson toTimerJson() {
        return new TimerJson(bookNumber, arrangementBegunAt);
    }
}
