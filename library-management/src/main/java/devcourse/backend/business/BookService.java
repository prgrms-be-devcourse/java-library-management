package devcourse.backend.business;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.BookDto;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void registerBook(BookDto data) {
        repository.addBook(new Book.Builder(data.getTitle(), data.getAuthor(), data.getTotalPages()).build());
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public List<Book> searchBooks(String keyword) { return repository.findByKeyword(keyword); }

    public void rentBook(long bookId) {
        repository.changeStatus(bookId, BookStatus.BORROWED);
    }

    public void returnBook(long bookId) {
        repository.changeStatus(bookId, BookStatus.ARRANGING);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repository.changeStatus(bookId, BookStatus.AVAILABLE);
            }
        }, 5 * 60 * 1000);
    }

    public void reportLoss(long bookId) {
        repository.changeStatus(bookId, BookStatus.LOST);
    }

    public void deleteBook(long bookId) {
        repository.deleteById(bookId);
    }
}
