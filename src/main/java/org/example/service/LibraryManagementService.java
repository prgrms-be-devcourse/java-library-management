package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookStatusType;
import org.example.exception.ExceptionCode;
import org.example.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LibraryManagementService {
    private Repository repository;
    private final Integer organizingMinutes = 5;

    public LibraryManagementService(Repository repository) {
        this.repository = repository;
    }

    // 도서 등록
    public void registerBook(Book book) {
        repository.saveBook(book);
    }

    // 전체 도서 목록 조회
    public List<Book> searchAllBooks() {
        return repository.findAllBooks();
    }

    // 제목으로 도서 검색
    public List<Book> searchBookBy(String title) {
        return repository.findBookByTitle(title);
    }

    // 도서 대여
    public void borrowBook(Integer bookId) {
        Optional<Book> bookToBorrow = repository.findBookById(bookId);
        bookToBorrow.ifPresent(book -> {
            if (book.canBorrow()) {
                repository.updateBookStatus(bookId, BookStatusType.BORROWING);
            } else {
                throw new IllegalArgumentException(ExceptionCode.getException(book.getStatus()).getMessage());
            }
        });
        bookToBorrow.orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
    }

    // 도서 반납
    public void returnBook(Integer bookId) {
        Optional<Book> bookToReturn = repository.findBookById(bookId);
        bookToReturn.ifPresent(book -> {
            if (book.canReturn()) {
                repository.updateBookStatus(bookId, BookStatusType.ORGANIZING);
                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(() -> {
                    repository.updateBookStatus(bookId, BookStatusType.BORROW_AVAILABE);
                    scheduler.shutdown();
                }, organizingMinutes, TimeUnit.MINUTES);
            } else {
                throw new IllegalArgumentException(ExceptionCode.getException(book.getStatus()).getMessage());
            }
        });
        bookToReturn.orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
    }

    // 도서 분실
    public void lostBook(Integer bookId) {
        Optional<Book> lostBook = repository.findBookById(bookId);
        lostBook.ifPresent(book -> {
            if (book.canLost()) {
                repository.updateBookStatus(bookId, BookStatusType.LOST);
            } else {
                throw new IllegalArgumentException(ExceptionCode.getException(book.getStatus()).getMessage());
            }
        });
        lostBook.orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));

    }

    // 도서 삭제
    public void deleteBook(Integer bookId) {
        Optional<Book> bookToDelete = repository.findBookById(bookId);
        bookToDelete.ifPresent(book -> {
            repository.deleteBookById(bookId);
        });
        bookToDelete.orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
    }

    public Integer getNextBookId() {
        return repository.getNextBookId();
    }
}
