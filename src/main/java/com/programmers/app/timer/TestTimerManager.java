package com.programmers.app.timer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestTimerManager implements TimerManger {

    private Queue<Timer> arrangementTimer;

    public TestTimerManager() {
        arrangementTimer = new LinkedList<>();
    }

    @Override
    public void add(Timer timer) {
        arrangementTimer.add(timer);
    }

    @Override
    public List<Long> popArrangedBooks(LocalDateTime now) {
        List<Long> arrangedBooks = new ArrayList<>();

        while(!arrangementTimer.isEmpty() && arrangementTimer.peek().isCompleted(now)) {
            arrangedBooks.add(arrangementTimer.remove().getBookNumber());
        }

        return arrangedBooks;
    }

    @Override
    public void remove(long bookNumber) {
        arrangementTimer.removeIf(t -> t.getBookNumber() == bookNumber);
    }
}
