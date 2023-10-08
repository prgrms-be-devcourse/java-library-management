package devcourse.backend.business;

import devcourse.backend.model.Book;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.MemoryRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.CreateBookDto;

import java.time.Clock;
import java.util.List;

import static devcourse.backend.FileSetting.*;
import static devcourse.backend.model.BookStatus.*;

public class BookService {
    private final int BOOK_ORGANIZATION_TIME = 5 * 60 * 10000;
    private final BookOrganizingScheduler scheduler;
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
        scheduler = new BookOrganizingScheduler(repository, BOOK_ORGANIZATION_TIME);
        scheduler.startScheduler();
    }

    public void registerBook(CreateBookDto data) {
        repository.findByTitleAndAuthor(data.getTitle(), data.getAuthor())
                .ifPresentOrElse(
                        (b) -> { throw new IllegalArgumentException("'" + b.getTitle() + "'는 이미 존재 하는 도서입니다."); } ,
                        () -> repository.addBook(new Book.Builder(data.getTitle(), data.getAuthor(), data.getTotalPages()).build())
                );
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
            repository.flush();
        } else throw new IllegalArgumentException(book.getStatus().toString());
    }

    public void returnBook(long bookId) {
        Book book = repository.findById(bookId).orElseThrow();

        if(book.getStatus().canSwitch(ARRANGING)) {
            book.changeStatus(ARRANGING);
            repository.flush();
        } else throw new IllegalArgumentException(book.getStatus().toString());

        if(!scheduler.isRunning()) scheduler.startScheduler();
    }

    public void reportLoss(long bookId) {
        Book book = repository.findById(bookId).orElseThrow();
        if(book.getStatus().canSwitch(LOST)) {
            book.changeStatus(LOST);
            repository.flush();
        } else throw new IllegalArgumentException(book.getStatus().toString());
    }

    public void deleteBook(long bookId) {
        repository.deleteById(bookId);
    }
}
