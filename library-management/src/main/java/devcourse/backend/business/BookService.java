package devcourse.backend.business;

import devcourse.backend.medel.Book;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.MemoryRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.BookDto;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static devcourse.backend.FileSetting.*;
import static devcourse.backend.medel.BookStatus.*;

public class BookService {
    private final Repository repository;

    public BookService(ModeType mode) {
        if(mode == ModeType.TEST_MODE) {
            this.repository = new MemoryRepository();
        } else this.repository = new FileRepository(FILE_PATH.getValue(), FILE_NAME.getValue());
    }

    public void registerBook(BookDto data) {
        repository.addBook(new Book.Builder(data.getTitle(), data.getAuthor(), data.getTotalPages()).build());
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public List<Book> searchBooks(String keyword) {
        return repository.findByKeyword(keyword);
    }

    public void rentBook(long bookId) {
        Book book = repository.findById(bookId).orElseThrow();

        if(book.getStatus().canSwitch(BORROWED)) {
            book.changeStatus(BORROWED);
        } else throw new IllegalArgumentException(book.getStatus().toString());
    }

    public void returnBook(long bookId) {
        Book book = repository.findById(bookId).orElseThrow();

        if(book.getStatus().canSwitch(ARRANGING)) {
            book.changeStatus(ARRANGING);
        } else throw new IllegalArgumentException(book.getStatus().toString());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                book.changeStatus(AVAILABLE);
            }
        }, 5 * 60 * 1000);
    }

    public void reportLoss(long bookId) {
        Book book = repository.findById(bookId).orElseThrow();
        if(book.getStatus().canSwitch(LOST)) {
            book.changeStatus(LOST);
        } else throw new IllegalArgumentException(book.getStatus().toString());
    }

    public void deleteBook(long bookId) {
        repository.deleteById(bookId);
    }
}
