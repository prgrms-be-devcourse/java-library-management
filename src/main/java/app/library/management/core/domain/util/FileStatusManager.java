package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.concurrent.TimeUnit;

public class FileStatusManager implements StatusManager {

    private final BookRepository bookRepository;

    public FileStatusManager(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(Book book) {
        scheduler.schedule(() -> {
            book.available();
            bookRepository.update(book);
            System.out.println("상태 변경 완료");
        }, 20, TimeUnit.SECONDS);
    }

//    private static final int POOL_SIZE = 256;
//    private final ScheduledExecutorService scheduler;
//
//    public StatusManager() {
//        this.scheduler = Executors.newScheduledThreadPool(POOL_SIZE);
//    }
//
//    public void execute(Book book) {
//        scheduler.schedule(() -> {
//            book.available();
//            System.out.println("상태 변경 완료");
//        }, 1, TimeUnit.MINUTES);
//    }
}
