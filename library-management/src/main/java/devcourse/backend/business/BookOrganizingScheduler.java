package devcourse.backend.business;

import devcourse.backend.model.Book;
import devcourse.backend.repository.Repository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static devcourse.backend.model.BookStatus.*;

public class BookOrganizingScheduler {
    private final Repository repository;
    private boolean isRunning;
    private ScheduledExecutorService scheduler;
    private Clock clock;

    public BookOrganizingScheduler(Repository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
        isRunning = false;
    }

    public void startScheduler() {
        if(!isRunning) {
            isRunning = true;
            scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            });

            scheduler.scheduleAtFixedRate(new BookOrganizingTask(), 0, 60, TimeUnit.SECONDS);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private class BookOrganizingTask implements Runnable {
        @Override
        public void run() {
            List<Book> booksToBeAvailable = repository.findAll()
                    .stream()
                    .filter(b -> b.getStatus() == ARRANGING)
                    .collect(Collectors.toList());

            long count = booksToBeAvailable.size();
            if (count == 0) {
                isRunning = false;
                scheduler.shutdown();
            }

            LocalDateTime now = LocalDateTime.now(clock);
            for (Book book : booksToBeAvailable) {
                if (now.isAfter(book.getUpdateAt().plusMinutes(1L))) {
                    book.changeStatus(AVAILABLE);
                }
            }
        }
    }
}
