package service;

import constant.ExceptionMsg;
import model.Book;
import model.Status;
import repository.Repository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void saveBook(String title, String author, int pageNum) {
        Book book = new Book(repository.createBookNo(), title, author, pageNum, Status.AVAILABLE);
        repository.saveBook(book);
    }

    public List<Book> findAllBook() {
        return repository.findAllBook();
    }

    public List<Book> findBooksByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    public void borrowBookByBookNo(Long bookNo) {
        Book book = findBookByBookNo(bookNo);

        if (book.isAvailableToBorrow()) {
            book.toBorrowed();
            repository.saveBook(book);
        }
    }

    public void returnBookByBookNo(Long bookNo, int time) {
        Book book = findBookByBookNo(bookNo);

        if (book.isAvailableToReturn()) {
            book.toOrganizing();
            repository.saveBook(book);
            scheduleTask(book, time);
        }
    }

    public void lostBookByBookNo(Long bookNo) {
        Book book = findBookByBookNo(bookNo);
        if (book.isAvailableToChangeLost()) {
            book.toLost();
            repository.saveBook(book);
        }
    }

    public void deleteBookByBookNo(Long bookNo) {
        if (findBookByBookNo(bookNo) != null) {
            repository.deleteBook(bookNo);
        }
    }

    private Book findBookByBookNo(Long bookNo) {
        return repository.findBookByBookNo(bookNo)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMsg.NO_TARGET.getMessage()));
    }

    private TimerTask wrap(Runnable runnable) {
        return new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    private void scheduleTask(Book book, int time) {
        Runnable bookTask = () -> {
            book.toAvailable();
            repository.saveBook(book);
        };
        Timer timer = new Timer(true);
        timer.schedule(wrap(bookTask), time);
    }
}
