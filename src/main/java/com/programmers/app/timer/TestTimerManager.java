package com.programmers.app.timer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestTimerManager implements TimerManger {

    private Queue<Timer> arrangementTimers;

    public TestTimerManager() {
        arrangementTimers = new LinkedList<>();
    }

    @Override
    public void add(Timer timer) {
        arrangementTimers.add(timer);
    }

    @Override
    public List<Long> popArrangedBooks(LocalDateTime now) {
        List<Long> arrangedBooks = new ArrayList<>();

        while(!arrangementTimers.isEmpty() && arrangementTimers.peek().isCompleted(now)) {
            arrangedBooks.add(arrangementTimers.remove().getBookNumber());
        }

        return arrangedBooks;
    }

    @Override
    public boolean remove(long bookNumber) {
        return arrangementTimers.removeIf(timer -> timer.isMatching(bookNumber));
    }
}
