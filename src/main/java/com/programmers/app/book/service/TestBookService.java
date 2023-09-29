package com.programmers.app.book.service;

import java.time.LocalDateTime;
import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.request.RequestBook;
import com.programmers.app.exception.ActionNotAllowedException;
import com.programmers.app.exception.BookNotFoundException;
import com.programmers.app.timer.Timer;
import com.programmers.app.timer.TimerManger;

public class TestBookService implements BookService {
    private final BookRepository bookRepository;
    private final TimerManger timerManger;

    public TestBookService(BookRepository bookRepository, TimerManger timerManger) {
        this.bookRepository = bookRepository;
        this.timerManger = timerManger;
    }

    @Override
    public void register(RequestBook requestBook) {
        long newBookNumber = bookRepository.getLastBookNumber() + 1L;
        bookRepository.add(requestBook.toBook(newBookNumber));
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks()
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> findByTitle(String title) {
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
    }

    @Override
    public void returnBook(long bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(BookNotFoundException::new);

        if (book.getStatus().equals(BookStatus.AVAILABLE) || book.getStatus().equals(BookStatus.ON_ARRANGEMENT)) {
            throw new ActionNotAllowedException("[System] 이미 반납된 도서입니다.");
        }

        book.setStatus(BookStatus.ON_ARRANGEMENT);
        timerManger.add(new Timer(bookNumber, LocalDateTime.now()));
    }

    @Override
    public void deleteBook(long bookNumber) {
        bookRepository.delete(bookRepository.findByBookNumber(bookNumber)
                        .orElseThrow(BookNotFoundException::new));
    }

    @Override
    public void reportLost(long bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(BookNotFoundException::new);

        if (book.getStatus().equals(BookStatus.LOST)) {
            throw new ActionNotAllowedException("[System] 분실 처리된 도서입니다.");
        }

        book.setStatus(BookStatus.LOST);
    }
}
