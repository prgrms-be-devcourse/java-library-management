package com.programmers.app.timer.dto;

import java.time.LocalDateTime;

import com.programmers.app.timer.Timer;

public class TimerJSON {

    private int bookNumber;
    private String arrangementBegunAt;

    public TimerJSON(int bookNumber, LocalDateTime localDateTime) {
        this.bookNumber = bookNumber;
        this.arrangementBegunAt = localDateTime.toString();
    }

    public Timer toTimer() {
        return new Timer(bookNumber, LocalDateTime.parse(arrangementBegunAt));
    }
}
