package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.concurrent.TimeUnit;

public class MemoryStatusManager implements StatusManager {

    private final BookRepository bookRepository;

    public MemoryStatusManager(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(Book book) {
        scheduler.schedule(() -> {
            book.available();
            System.out.println("상태 변경 완료");
        }, 20, TimeUnit.SECONDS);
    }
}
