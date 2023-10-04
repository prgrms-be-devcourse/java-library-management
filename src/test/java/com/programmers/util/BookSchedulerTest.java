package com.programmers.util;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookSchedulerTest {

    private BookScheduler bookScheduler;
    private ScheduledExecutorService mockedScheduler;

    @BeforeEach
    void setUp() {
        // TODO 테스트 위해 주입 식으로 변경
        mockedScheduler = mock(ScheduledExecutorService.class);
        bookScheduler = new BookScheduler(mockedScheduler);
    }

    @Test
    void testScheduleBookAvailableTask() {
        Runnable task = mock(Runnable.class);
        bookScheduler.scheduleBookAvailableTask(task);

        //TODO 실제로 스케줄링이 되었는지 확인하는 방법은?
        verify(mockedScheduler, times(1)).schedule(
            eq(task),
            eq(1000 * 60 * 5L),
            eq(TimeUnit.MILLISECONDS)
        );
    }
}
