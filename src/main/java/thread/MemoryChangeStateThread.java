package thread;

import domain.BookState;
import domain.Book;

public class MemoryChangeStateThread extends Thread {
    private final Book book;
    private int time;
    public MemoryChangeStateThread(Book book, int time) {
        this.book = book;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            book.changeStateToAvailable();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
