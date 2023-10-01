package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookStatusType;
import org.example.exception.ExceptionCode;
import org.example.repository.Repository;

import java.util.List;
import java.util.Optional;

public class LibraryManagementService {
    private Repository repository;

    public LibraryManagementService(Repository repository) {
        this.repository = repository;
    }

    // 도서 등록
    public void registerBook(Book book) {
        repository.saveBook(book);
    }

    // 전체 도서 목록 조회
    public List<Book> searchBooks() {
        return repository.findAllBooks();
    }

    // 제목으로 도서 검색
    public List<Book> searchBookByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    // 도서 대여
    public Optional<ExceptionCode> borrowBook(Integer bookId) {
        Optional<Book> bookToBorrow = repository.findBookById(bookId);
        return bookToBorrow.map(book -> {
            if (book.canBorrow()) {
                repository.updateBookStatus(bookId, BookStatusType.BORROWING);
                return Optional.<ExceptionCode>empty();
            } else {
                return Optional.of(ExceptionCode.getException(book.getStatus()));
            }
        }).orElse(Optional.of(ExceptionCode.INVALID_BOOK));
    }

    // 도서 반납
    public Optional<ExceptionCode> returnBook(Integer bookId) {
        Optional<Book> bookToReturn = repository.findBookById(bookId);
        return bookToReturn.map(book -> {
            if (book.canReturn()) {
                repository.updateBookStatus(bookId, BookStatusType.ORGANIZING);
                return Optional.<ExceptionCode>empty();
            } else {
                return Optional.of(ExceptionCode.getException(book.getStatus()));
            }
        }).orElse(Optional.of(ExceptionCode.INVALID_BOOK));
    }

    // 도서 분실
    public Optional<ExceptionCode> lostBook(Integer bookId) {
        Optional<Book> lostBook = repository.findBookById(bookId);
        return lostBook.map(book -> {
            if (book.canLost()) {
                repository.updateBookStatus(bookId, BookStatusType.LOST);
                return Optional.<ExceptionCode>empty();
            } else {
                return Optional.of(ExceptionCode.getException(book.getStatus()));
            }
        }).orElse(Optional.of(ExceptionCode.INVALID_BOOK));
    }

    // 도서 삭제
    public Optional<ExceptionCode> deleteBook(Integer bookId) {
        Optional<Book> bookToDelete = repository.findBookById(bookId);
        return bookToDelete.map(book -> {
            repository.deleteBookById(bookId);
            return Optional.<ExceptionCode>empty();
        }).orElse(Optional.of(ExceptionCode.INVALID_BOOK));
    }

    public Integer getNextBookId() {
        return repository.getNextBookId();
    }
}
