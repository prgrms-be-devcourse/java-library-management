package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.repository.Repository;

import java.util.List;

import static com.programmers.library.domain.Book.*;
import static com.programmers.library.domain.BookStatusType.*;

public class LibraryService {
    private final Repository repository;

    public LibraryService(Repository repository) {
        this.repository = repository;
    }

    public void registerBook(String title, String author, Integer page) {
        checkBookInfo(title, author, page);

        Long lastId = repository.findLastId() + 1;

        repository.register(createRentableBook(lastId, title, author, page));
    }

    public List<Book> findAllBooks() {
        return repository.findAllBooks();
    }

    public List<Book> findBooksByTitle(String title) {
        return repository.findBooksByTitle(title);
    }

    public Book findBookById(Long id) {
        return repository.findBookById(id).orElseThrow(() -> ExceptionHandler.err(ErrorCode.BOOK_NOT_FOUND));
    }

    public void deleteBook(Long id) {
        repository.findBookById(id).orElseThrow(() -> ExceptionHandler.err(ErrorCode.BOOK_NOT_FOUND));
        repository.deleteBook(id);
    }

    public void rentalBook(Long id){
        Book rentalBook = repository.findBookById(id).orElseThrow(() -> ExceptionHandler.err(ErrorCode.BOOK_NOT_FOUND));

        switch (rentalBook.getBookStatus()) {
            case RENTABLE -> {
                repository.updateStatus(rentalBook, rentalBook.getBookStatus(), RENTED);
            }
            case RENTED -> {
                throw ExceptionHandler.err(ErrorCode.RENTAL_FAILED_ALREADY_RENTED_EXCEPTION);
            }
            case ORGANIZING -> {
                throw ExceptionHandler.err(ErrorCode.RENTAL_FAILED_ORGANIZING_BOOK_EXCEPTION);
            }
            case LOST -> {
                throw ExceptionHandler.err(ErrorCode.RENTAL_FAILED_LOST_BOOK_EXCEPTION);
            }
        }
    }

    public Book returnBook(Long id){
        Book returnBook = repository.findBookById(id).orElseThrow(() -> ExceptionHandler.err(ErrorCode.BOOK_NOT_FOUND));

        switch (returnBook.getBookStatus()) {
            case RENTED, LOST -> {
                repository.updateStatus(returnBook, returnBook.getBookStatus(), ORGANIZING);
            }
            case RENTABLE -> {
                throw ExceptionHandler.err(ErrorCode.ALREADY_AVAILABLE_RENTAL_BOOK_EXCEPTION);
            }
            case ORGANIZING -> {
                throw ExceptionHandler.err(ErrorCode.ALREADY_RETURN_ORGANIZING_BOOK_EXCEPTION);
            }
        }

        return returnBook;
    }

    public void lostBook(Long id){
        Book lostBook = repository.findBookById(id).orElseThrow(() -> ExceptionHandler.err(ErrorCode.BOOK_NOT_FOUND));

        switch (lostBook.getBookStatus()) {
            case RENTED -> {
                repository.updateStatus(lostBook, lostBook.getBookStatus(), LOST);
            }
            case RENTABLE, ORGANIZING -> {
                throw ExceptionHandler.err(ErrorCode.LOST_FAILED_EXCEPTION);
            }
            case LOST -> {
                throw ExceptionHandler.err(ErrorCode.ALREADY_LOST_EXCEPTION);
            }
        }
    }

    private static void checkBookInfo(String title, String author, Integer page) {
        if(title.isEmpty()){
            throw ExceptionHandler.err(ErrorCode.INVALID_BOOK_TITLE_EXCEPTION);
        }
        if(author.isEmpty()) {
            throw ExceptionHandler.err(ErrorCode.INVALID_BOOK_AUTHOR_EXCEPTION);
        }
        if(page <= 0) {
            throw ExceptionHandler.err(ErrorCode.NEGATIVE_PAGE_EXCEPTION);
        }
        if(title.length() > 100) {
            throw ExceptionHandler.err(ErrorCode.LIMIT_TITLE_LENGTH_EXCEPTION);
        }
    }
}
