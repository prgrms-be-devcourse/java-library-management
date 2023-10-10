package thread;

import domain.Book;
import exception.ThreadInterruptException;

import static repository.FileRepository.updateFile;

public class FileChangeStateThread extends Thread {
    private final Book book;
    private int time;

    public FileChangeStateThread(Book book, int time) {
        this.book = book;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(time);
            book.changeStateToAvailable();
            updateFile();
        } catch (InterruptedException e) {
            throw new ThreadInterruptException();
        }
    }
}
