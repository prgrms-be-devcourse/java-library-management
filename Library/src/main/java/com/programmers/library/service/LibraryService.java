package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.repository.Repository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.programmers.library.domain.BookStatus.RENTABLE;

public class LibraryService {
    private final Repository repository;
    private static final int ORGANIZING_TIME = 5 * 60 * 1000;

    public LibraryService(Repository repository) {
        this.repository = repository;
    }

    public void registerBook(String title, String author, Integer page) {
        Long lastId = repository.findLastId() + 1;

        Book book = new Book(lastId, title, author, page);
        repository.register(book);
    }

    public void updateStatus(Book book, BookStatus bookStatus){
        repository.updateStatus(book, bookStatus);
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

    public void completeOrganizing(Book book) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 5분 후에 실행되는 코드
                repository.updateStatus(book, RENTABLE);
            }
        }, ORGANIZING_TIME); // 5분(밀리초 단위)
    }

}
