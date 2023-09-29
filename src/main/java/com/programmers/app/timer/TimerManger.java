package com.programmers.app.timer;

import java.time.LocalDateTime;
import java.util.List;

public interface TimerManger {

    void add(Timer timer);

    List<Long> popArrangedBooks(LocalDateTime now);

    void remove(long bookNumber);
}
