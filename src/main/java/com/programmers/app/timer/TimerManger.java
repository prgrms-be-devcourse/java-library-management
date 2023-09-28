package com.programmers.app.timer;

import java.util.List;

public interface TimerManger {

    void loadTimersFromFile();

    void add(Timer timer);

    List<Long> popEliminated();
}
