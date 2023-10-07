package thread;

import domain.BookState;
import exception.ThreadInterruptException;
import repository.Book;

import static repository.FileRepository.updateFile;

public class FileChangeStateThread extends Thread {
    private final Book book;

    public FileChangeStateThread(Book book) {
        this.book = book;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.setState(BookState.AVAILABLE);
            updateFile();
        } catch (InterruptedException e) {
            throw new ThreadInterruptException();
        }
    }
}
