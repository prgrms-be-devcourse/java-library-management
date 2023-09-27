package thread;

import repository.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static repository.NormalRepository.updateFile;

public class NormalChangeStateThread extends Thread {
    private Book book;
    private File file;
    private List<Book> books;
    public NormalChangeStateThread(Book book, File file, List<Book> books) {
        this.book = book;
        this.file = file;
        this.books = books;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            book.setState("대여 가능");
            updateFile(books, file);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
