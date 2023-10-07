package thread;

import domain.BookState;
import domain.Book;

public class MemoryChangeStateThread extends Thread {
    private final Book book;

    public MemoryChangeStateThread(Book book) {
        this.book = book;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.changeStateToAvailable();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
