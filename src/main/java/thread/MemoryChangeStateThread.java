package thread;

import domain.BookState;
import repository.Book;

public class MemoryChangeStateThread extends Thread {
    private Book book;

    public MemoryChangeStateThread(Book book) {
        this.book = book;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.setState(BookState.AVAILABLE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
