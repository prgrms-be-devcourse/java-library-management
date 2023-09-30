package com.programmers.app.timer;

import static com.programmers.app.config.AppConfig.MINUTES_FOR_BOOK_ARRANGEMENT;

import java.time.LocalDateTime;

import com.programmers.app.timer.dto.TimerJson;

public class Timer {

    private int bookNumber;
    private LocalDateTime arrangementBegunAt;

    public Timer (int bookNumber, LocalDateTime arrangementBegunAt) {
        this.bookNumber = bookNumber;
        this.arrangementBegunAt = arrangementBegunAt;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public boolean isCompleted(LocalDateTime localDateTime) {
        LocalDateTime completedAt = arrangementBegunAt.plusMinutes(MINUTES_FOR_BOOK_ARRANGEMENT);
        return completedAt.isBefore(localDateTime);
    }

    public boolean isMatching(int bookNumber) {
        return this.bookNumber == bookNumber;
    }

    public TimerJson toTimerJson() {
        return new TimerJson(bookNumber, arrangementBegunAt);
    }
}
