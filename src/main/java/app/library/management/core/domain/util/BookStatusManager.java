package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BookStatusManager {

    private static final int POOL_SIZE = 256;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);

    private final BookRepository bookRepository;

    public BookStatusManager(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void execute(Book book) {
        scheduler.schedule(() -> {
            book.available();
            bookRepository.update(book);
            System.out.println("상태 변경 완료");
        }, 10, TimeUnit.SECONDS);
    }
}
