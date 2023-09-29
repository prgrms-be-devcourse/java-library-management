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
        long newBookNumber = bookRepository.getLastBookNumber() + 1L;
        bookRepository.add(bookRequest.toBook(newBookNumber));

        bookRepository.save();
    }

    @Override
    public List<Book> findAllBooks() {
        if (updateArrangementCompleted()) {
            timerManger.save();
        }

        return bookRepository.findAllBooks()
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> findByTitle(String title) {
        if (updateArrangementCompleted()) {
            timerManger.save();
        }

        return bookRepository.findByTitle(title)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void borrowBook(long bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(BookNotFoundException::new);

        if (book.getStatus().equals(BookStatus.ON_LOAN)) {
            throw new ActionNotAllowedException("[System] 이미 대여중인 도서입니다.");
        }

        if (book.getStatus().equals(BookStatus.LOST)) {
            throw new ActionNotAllowedException("[System] 분실 처리된 도서입니다.");
        }

        if (book.getStatus().equals(BookStatus.ON_ARRANGEMENT)) {
            throw new ActionNotAllowedException("[System] 정리중인 도서입니다.");
        }

        book.setStatus(BookStatus.ON_LOAN);
        bookRepository.save();
    }

    @Override
    public void returnBook(long bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(BookNotFoundException::new);

        if (book.getStatus().equals(BookStatus.IN_PLACE) || book.getStatus().equals(BookStatus.ON_ARRANGEMENT)) {
            throw new ActionNotAllowedException("[System] 이미 반납된 도서입니다.");
        }

        book.setStatus(BookStatus.ON_ARRANGEMENT);
        timerManger.add(new Timer(bookNumber, LocalDateTime.now()));

        bookRepository.save();
        timerManger.save();
    }

    @Override
    public void deleteBook(long bookNumber) {
        bookRepository.delete(bookRepository.findByBookNumber(bookNumber)
                        .orElseThrow(BookNotFoundException::new));

        bookRepository.save();
        if (timerManger.remove(bookNumber)) {
            timerManger.save();
        }
    }

    @Override
    public void reportLost(long bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(BookNotFoundException::new);

        if (book.getStatus().equals(BookStatus.LOST)) {
            throw new ActionNotAllowedException("[System] 분실 처리된 도서입니다.");
        }

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
                        .orElseThrow(BookNotFoundException::new)) //todo: proper exception to be raised
                .forEach(book -> {
                    book.setStatus(BookStatus.IN_PLACE);
                    changed.set(true);
                });

        return changed.get();
    }
}
