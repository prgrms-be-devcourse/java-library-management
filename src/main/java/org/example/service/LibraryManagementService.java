package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookStatusType;
import org.example.exception.ExceptionCode;
import org.example.repository.Repository;

import java.util.List;
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
        Book bookToBorrow = repository.findBookById(bookId).orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
        if (bookToBorrow.canBorrow()) {
            repository.updateBookStatus(bookId, BookStatusType.BORROWING);
        } else {
            throw new IllegalArgumentException(ExceptionCode.getException(bookToBorrow.getStatus()).getMessage());
        }
    }

    // 도서 반납
    public void returnBook(Integer bookId) {
        Book bookToReturn = repository.findBookById(bookId).orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
        if (bookToReturn.canReturn()) {
            repository.updateBookStatus(bookId, BookStatusType.ORGANIZING);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                repository.updateBookStatus(bookId, BookStatusType.BORROW_AVAILABE);
                scheduler.shutdown();
            }, organizingMinutes, TimeUnit.MINUTES);
        } else {
            throw new IllegalArgumentException(ExceptionCode.getException(bookToReturn.getStatus()).getMessage());
        }
    }

    // 도서 분실
    public void lostBook(Integer bookId) {
        Book lostBook = repository.findBookById(bookId).orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
        if (lostBook.canLost()) {
            repository.updateBookStatus(bookId, BookStatusType.LOST);
        } else {
            throw new IllegalArgumentException(ExceptionCode.getException(lostBook.getStatus()).getMessage());
        }

    }

    // 도서 삭제
    public void deleteBook(Integer bookId) {
        repository.findBookById(bookId).orElseThrow(() -> new IllegalArgumentException(ExceptionCode.INVALID_BOOK.getMessage()));
        repository.deleteBookById(bookId);
    }

    public Integer getNextBookId() {
        return repository.getNextBookId();
    }
}
