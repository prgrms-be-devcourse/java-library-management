package com.programmers.application;

import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.unchecked.BookNotFoundException;
import com.programmers.exception.unchecked.DuplicateBookException;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.util.BookScheduler;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookScheduler bookScheduler;

    public Long registerBook(RegisterBookReq registerBookReq) {
        repository.findByTitle(registerBookReq.getTitle()).stream()
            .filter(book -> book.getTitle().equals(registerBookReq.getTitle()))
            .findAny()
            .ifPresent(book -> {
                throw new DuplicateBookException();
            });
        var book = repository.save(registerBookReq.toBook());
        return book.getId();
    }

    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    public List<Book> searchBook(String title) {
        return repository.findByTitle(title);
    }

    public void deleteBook(Long id) {
        if (repository.deleteById(id) == 0) {
            throw new BookNotFoundException();
        }
    }

    // 대여
    public void rentBook(Long id) {
        updateBookStatus(id, Book::updateBookStatusToRent);
    }

    // 반납
    public void returnBook(Long id) {
        updateBookStatus(id, this::organizeBook);
    }

    // 분실
    public void reportLostBook(Long id) {
        updateBookStatus(id, Book::updateBookStatusToLost);
    }

    private void updateBookStatus(Long id, Consumer<Book> statusUpdater) {
        repository.findById(id).ifPresentOrElse(book -> {
            statusUpdater.accept(book);
            repository.update(book);
        }, () -> {
            throw new BookNotFoundException();
        });
    }

    private void updateBookStatusToAvailable(Long id) {
        // 삭제 되었을때를 고려.
        repository.findById(id).ifPresent(book -> {
            book.updateBookStatusToAvailable();
            repository.update(book);
        });
    }

    private void organizeBook(Book book) {
        book.updateBookStatusToOrganizing();
        scheduleStatusUpdateToAvailable(book);
    }

    private void scheduleStatusUpdateToAvailable(Book book) {
        bookScheduler.scheduleBookAvailableTask(() -> updateBookStatusToAvailable(book.getId()));
    }
}
