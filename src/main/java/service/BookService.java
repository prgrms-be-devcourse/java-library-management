package service;

import constant.ExceptionMsg;
import model.Book;
import repository.Repository;
import util.BookScheduler;

import java.util.List;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void saveBook(String title, String author, int pageNum) {
        Book book = new Book(repository.createBookNo(), title, author, pageNum);
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

    public void returnBookByBookNo(Long bookNo, BookScheduler bookScheduler) {
        Book book = findBookByBookNo(bookNo);
        if (book.isAvailableToReturn()) {
            book.toOrganizing();
            repository.saveBook(book);
            bookScheduler.scheduleBookTask(getBookTask(book));
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

    private Runnable getBookTask(Book book) {
        return () -> {
            book.toAvailable();
            repository.saveBook(book);
        };
    }
}
