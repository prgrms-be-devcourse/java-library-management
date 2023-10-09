package com.programmers.app.book.service;

import java.util.List;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.dto.BookRequest;
import com.programmers.app.exception.ActionNotAllowedException;
import com.programmers.app.exception.BookNotFoundException;
import com.programmers.app.exception.messages.ExceptionMessages;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void exit() {
        bookRepository.save();
    }

    public void register(BookRequest bookRequest) {
        int newBookNumber = bookRepository.getLastBookNumber() + 1;
        bookRepository.add(bookRequest.toBook(newBookNumber));

        bookRepository.save();
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks()
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.NO_BOOK_STORED));
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.TITLE_NONEXISTENT));
    }

    public void borrowBook(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateBorrowable(book);

        bookRepository.updateBookStatus(book, BookStatus.BORROWED);
        bookRepository.save();
    }

    public void returnBook(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateReturnable(book);

        bookRepository.updateBookStatus(book, BookStatus.ON_ARRANGEMENT);
        bookRepository.save();
    }

    public void deleteBook(int bookNumber) {
        bookRepository.delete(bookRepository.findByBookNumber(bookNumber)
                        .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT)));

        bookRepository.save();
    }

    public void reportLost(int bookNumber) {
        Book book = bookRepository.findByBookNumber(bookNumber)
                .orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NUMBER_NONEXISTENT));

        validateReportable(book);

        bookRepository.updateBookStatus(book, BookStatus.LOST);
        bookRepository.save();
    }

    private void validateBorrowable(Book book) {
        if (book.isBorrowed()) {
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
        if (book.isOnArrangement()) {
            throw new ActionNotAllowedException(ExceptionMessages.ALREADY_RETURNED);
        }

        if (book.isInPlace()) {
            throw new ActionNotAllowedException(ExceptionMessages.BOOK_IN_PLACE);
        }
    }

    private void validateReportable(Book book) {
        if (book.isLost()) {
            throw new ActionNotAllowedException(ExceptionMessages.ALREADY_REPORTED_LOST);
        }
    }
}
