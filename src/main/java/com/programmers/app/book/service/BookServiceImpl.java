package com.programmers.app.book.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.dto.BookRequest;
import com.programmers.app.exception.ActionNotAllowedException;
import com.programmers.app.exception.BookNotFoundException;
import com.programmers.app.exception.messages.ExceptionMessages;
import com.programmers.app.timer.Timer;
import com.programmers.app.timer.TimerManger;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final TimerManger timerManger;

    public BookServiceImpl(BookRepository bookRepository, TimerManger timerManger) {
        this.bookRepository = bookRepository;
        this.timerManger = timerManger;
    }

    @Override
    public void register(BookRequest bookRequest) {
        int newBookNumber = bookRepository.getLastBookNumber() + 1;
        bookRepository.add(bookRequest.toBook(newBookNumber));

        bookRepository.save();
    }

    @Override
    public List<Book> findAllBooks() {
        if (updateArrangementCompleted()) {
            timerManger.save();
        }

        return bookRepository.findAllBooks()
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.NO_BOOK_LOADED));
    }

    @Override
    public List<Book> findByTitle(String title) {
        if (updateArrangementCompleted()) {
            timerManger.save();
        }

        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.TITLE_NONEXISTENT));
    }

    @Override
    public void borrowBook(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateBorrowable(book);

        book.setStatus(BookStatus.ON_LOAN);
        bookRepository.save();
    }

    @Override
    public void returnBook(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateReturnable(book);

        book.setStatus(BookStatus.ON_ARRANGEMENT);
        timerManger.add(new Timer(bookNumber, LocalDateTime.now()));

        bookRepository.save();
        timerManger.save();
    }

    @Override
    public void deleteBook(int bookNumber) {
        bookRepository.delete(bookRepository.findByBookNumber(bookNumber)
                        .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT)));

        bookRepository.save();
        if (timerManger.remove(bookNumber)) {
            timerManger.save();
        }
    }

    @Override
    public void reportLost(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateReportable(book);

        book.setStatus(BookStatus.LOST);

        bookRepository.save();
        if (timerManger.remove(bookNumber)) {
            timerManger.save();
        }
    }

    //todo: if timer exists, but not book?
    @Override
    public boolean updateArrangementCompleted() {
        AtomicBoolean changed = new AtomicBoolean(false);

        timerManger.popArrangedBooks(LocalDateTime.now())
                .stream()
                .map(bookNumber -> bookRepository.findByBookNumber(bookNumber)
                        .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT)))
                .forEach(book -> {
                    book.setStatus(BookStatus.IN_PLACE);
                    changed.set(true);
                });

        return changed.get();
    }

    private void validateBorrowable(Book book) {
        if (book.isOnLoan()) {
            throw new ActionNotAllowedException(ExceptionMessages.ALREADY_BORROWED);
        }

        if (book.isLost()) {
            throw new ActionNotAllowedException(ExceptionMessages.BOOK_LOSS_REPORTED);
        }

        if (book.isOnArrangement()) {
            throw new ActionNotAllowedException(ExceptionMessages.BOOK_ON_ARRANGEMENT);
        }
    }

    private void validateReturnable(Book book) {
        if (book.isInPlace() || book.isOnArrangement()) {
            throw new ActionNotAllowedException(ExceptionMessages.ALREADY_RETURNED);
        }
    }

    private void validateReportable(Book book) {
        if (book.isLost()) {
            throw new ActionNotAllowedException(ExceptionMessages.ALREADY_REPORTED_LOST);
        }
    }
}
