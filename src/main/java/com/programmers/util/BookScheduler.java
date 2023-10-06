package com.programmers.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookScheduler {

    // TODO: 소멸 되었을떄....
    private final ScheduledExecutorService scheduler;
    private final int BOOK_ORGANIZING_TIME = 1000 * 60 * 5;

    public BookScheduler() {
        this(Executors.newScheduledThreadPool(100));
    }

    public void scheduleBookAvailableTask(Runnable task) {
        scheduler.schedule(task, BOOK_ORGANIZING_TIME, TimeUnit.MILLISECONDS);
    }
}

