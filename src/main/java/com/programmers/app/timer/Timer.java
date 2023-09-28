package com.programmers.app.timer;

import java.time.LocalDateTime;

public class Timer {

    private long bookNumber;
    private LocalDateTime arrangementBegunAt;

    public Timer (long bookNumber, LocalDateTime arrangementBegunAt) {
        this.bookNumber = bookNumber;
        this.arrangementBegunAt = arrangementBegunAt;
    }
}
