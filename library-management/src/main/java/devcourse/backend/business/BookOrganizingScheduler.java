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
    private ScheduledExecutorService executorService;
    private final Repository repository;
    private boolean isRunning;
    private int organizationTime;
    private int period;

    public BookOrganizingScheduler(Repository repository, int organizationTime, int period) {
        this.repository = repository;
        this.organizationTime = organizationTime;
        this.period = period;
        isRunning = false;
    }

    public void startScheduler() {
        if(!isRunning) {
            isRunning = true;
            executorService = Executors.newSingleThreadScheduledExecutor(runnable -> {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            });

            executorService.scheduleAtFixedRate(new BookOrganizingTask(), 0, period, TimeUnit.MINUTES);

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
                executorService.shutdown();
            }

            LocalDateTime now = LocalDateTime.now();
            for (Book book : booksToBeAvailable) {
                if (now.isAfter(book.getUpdateAt().plusMinutes(organizationTime))) {
                    book.changeStatus(AVAILABLE);
                }
            }
        }
    }
}
