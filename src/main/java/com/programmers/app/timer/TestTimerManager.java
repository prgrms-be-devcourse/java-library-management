package com.programmers.app.timer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestTimerManager implements TimerManger {

    private Queue<Timer> arrangementTimer;

    public TestTimerManager() {
        arrangementTimer = new LinkedList<>();
    }

    @Override
    public void loadTimersFromFile() {

    }

    @Override
    public void add(Timer timer) {
        arrangementTimer.add(timer);
    }

    @Override
    public List<Long> popEliminated() {
        return null;
    }
}
