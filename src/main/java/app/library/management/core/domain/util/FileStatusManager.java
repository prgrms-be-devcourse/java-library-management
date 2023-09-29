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
        }, 5, TimeUnit.MINUTES);
    }
}
