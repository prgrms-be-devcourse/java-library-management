package thread;

import repository.Book;

public class TestChangeStateThread extends Thread {
    private Book book;

    public TestChangeStateThread(Book book) {
        this.book = book;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.setState("대여 가능");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
