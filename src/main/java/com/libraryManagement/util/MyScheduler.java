package com.libraryManagement.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyScheduler {
    private final ScheduledExecutorService scheduler;

    public MyScheduler() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void scheduleTask(Runnable task, long delay, TimeUnit timeUnit) {
        scheduler.schedule(task, delay, timeUnit);
    }
}
