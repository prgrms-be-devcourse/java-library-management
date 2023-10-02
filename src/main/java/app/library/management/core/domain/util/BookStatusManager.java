package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookStatusManager {
    private final BookRepository bookRepository;
    private final ScheduledExecutorService scheduler;

    public BookStatusManager(BookRepository bookRepository, ScheduledExecutorService scheduler) {
        this.bookRepository = bookRepository;
        this.scheduler = scheduler;
    }

    public void execute(Book book) {
        scheduler.schedule(() -> {
            book.available();
            bookRepository.update(book);
        }, 5, TimeUnit.MINUTES);
    }
}
