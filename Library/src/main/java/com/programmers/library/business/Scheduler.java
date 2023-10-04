package com.programmers.library.business;

import com.programmers.library.domain.Book;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.file.FileHandler;
import com.programmers.library.file.FileUtil;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.Repository;
import com.programmers.library.service.LibraryService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.programmers.library.domain.BookStatusType.ORGANIZING;
import static com.programmers.library.domain.BookStatusType.RENTABLE;

public class Scheduler {
    private static final int ORGANIZING_TIME = 5;
    private static final int CORE_POOL_SIZE = 4;
    private final Repository repository;
    private final ScheduledExecutorService executorService;

    public Scheduler() {
        this.repository = new FileRepository(new FileHandler(new FileUtil()));
        this.executorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
    }

    public void completeOrganizing(Book book) {
        synchronized (this) {
            if (book.getBookStatus() != ORGANIZING) {
                throw ExceptionHandler.err(ErrorCode.BOOK_NOT_ORGANIZING_EXCEPTION);
            }

            executorService.schedule(() -> {
                repository.updateStatus(book, ORGANIZING, RENTABLE);
                executorService.shutdown();
            }, ORGANIZING_TIME, TimeUnit.MINUTES);
        }
    }
}
