package com.programmers.util;

import static org.mockito.Mockito.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookSchedulerTest {

    private BookScheduler bookScheduler;
    private ScheduledExecutorService mockedScheduler;

    @BeforeEach
    void setUp() {
        mockedScheduler = mock(ScheduledExecutorService.class);
        bookScheduler = new BookScheduler(mockedScheduler);
    }

    @Test
    void testScheduleBookAvailableTask() {
        Runnable task = mock(Runnable.class);
        bookScheduler.scheduleBookAvailableTask(task);

        verify(mockedScheduler, times(1)).schedule(
            eq(task),
            eq(1000 * 60 * 5L),
            eq(TimeUnit.MILLISECONDS)
        );
    }
}
