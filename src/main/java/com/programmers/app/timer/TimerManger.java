package com.programmers.app.timer;

import java.time.LocalDateTime;
import java.util.List;

public interface TimerManger {

    void add(Timer timer);

    List<Integer> popArrangedBooks(LocalDateTime now);

    boolean remove(int bookNumber);

    default void save() {}
}
