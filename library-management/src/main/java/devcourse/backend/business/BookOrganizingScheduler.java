package devcourse.backend.business;

import devcourse.backend.model.Book;
import devcourse.backend.repository.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static devcourse.backend.model.BookStatus.*;

public class BookOrganizingScheduler {
    private final Repository repository;
    private Timer timer;
    private boolean isRunning;

    public BookOrganizingScheduler(Repository repository) {
        timer = new Timer(true);
        this.repository = repository;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void startScheduler() {
        if(!isRunning) {
            isRunning = true;
            timer.schedule(new BookOrganizingTask(), 0, 60 * 1000);
        }
    }

    private class BookOrganizingTask extends TimerTask {
        @Override
        public void run() {
            List<Book> booksToBeAvailable = repository.findAll()
                    .stream()
                    .filter(b -> b.getStatus() == ARRANGING)
                    .collect(Collectors.toList());

            long count = booksToBeAvailable.size();
            if (count == 0) {
                isRunning = false;
                return;
            }

            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            for (Book book : booksToBeAvailable) {
                if (now.isAfter(book.getUpdateAt().plusMinutes(5L))) {
                    book.changeStatus(AVAILABLE);
                }
            }
        }
    }
}
