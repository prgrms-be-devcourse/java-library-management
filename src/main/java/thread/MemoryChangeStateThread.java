package thread;

import domain.Book;

public class MemoryChangeStateThread extends Thread {
    private final Book book;
    private int time;
    public MemoryChangeStateThread(Book book, int time) {
        this.book = book;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            book.changeStateToAvailable();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
