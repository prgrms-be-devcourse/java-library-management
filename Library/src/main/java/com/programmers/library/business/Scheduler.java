package com.programmers.library.business;

import com.programmers.library.domain.Book;
import com.programmers.library.service.LibraryService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.programmers.library.domain.BookStatusType.RENTABLE;

public class Scheduler {
    private static final int ORGANIZING_TIME = 5;
    private static final int CORE_POOL_SIZE = 4;

    private final LibraryService libraryService;
    private final ScheduledExecutorService executorService;

    public Scheduler(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
    }

    public void completeOrganizing(Book book) {
        executorService.schedule(() -> {
            libraryService.updateStatus(book, RENTABLE);
            executorService.shutdown();
        }, ORGANIZING_TIME, TimeUnit.MINUTES);
    }
}
