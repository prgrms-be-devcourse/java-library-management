package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookStatusManager {
    private final BookRepository bookRepository;
    private final ScheduledExecutorService scheduler;
    private final int delayMillisecond;

    public BookStatusManager(BookRepository bookRepository, ScheduledExecutorService scheduler, int delayMillisecond) {
        this.bookRepository = bookRepository;
        this.scheduler = scheduler;
        this.delayMillisecond = delayMillisecond;
    }

    public void execute(Book book, LocalDateTime now) {
        scheduler.schedule(() -> {
            book.available(now);
            bookRepository.update(book);
        }, TimeUnit.MILLISECONDS.toSeconds(delayMillisecond), TimeUnit.SECONDS);
    }
}
