package devcourse.backend.business;

import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.BookDto;

import java.util.List;
import java.util.NoSuchElementException;
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
        repository.findById(bookId).changeStatus(BookStatus.BORROWED);
        repository.modify();
    }

    public void returnBook(long bookId) {
        repository.findById(bookId).changeStatus(BookStatus.ARRANGING);
        repository.modify();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repository.findById(bookId).changeStatus(BookStatus.AVAILABLE);
            }
        }, 5 * 60 * 1000);
    }

    public void reportLoss(long bookId) {
        repository.findById(bookId).changeStatus(BookStatus.LOST);
        repository.modify();
    }

    public void deleteBook(long bookId) {
        repository.deleteById(bookId);
    }
}
