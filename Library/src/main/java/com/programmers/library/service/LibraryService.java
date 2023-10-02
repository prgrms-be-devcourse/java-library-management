package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatusType;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.repository.Repository;

import java.util.List;

public class LibraryService {
    private final Repository repository;

    public LibraryService(Repository repository) {
        this.repository = repository;
    }

    public void registerBook(String title, String author, Integer page) {
        Long lastId = repository.findLastId() + 1;

        repository.register(new Book(lastId, title, author, page));
    }

    public void updateStatus(Book book, BookStatusType bookStatus){
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
}
