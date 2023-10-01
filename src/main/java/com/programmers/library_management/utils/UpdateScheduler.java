package com.programmers.library_management.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateScheduler {
    private final ScheduledExecutorService scheduledExecutorService;

    public UpdateScheduler() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void execute(Runnable runnable) {
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.MINUTES);
    }

    public void shutdown() {
        scheduledExecutorService.shutdownNow();
    }
}
