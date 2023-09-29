package com.programmers.app.book.service;

import java.util.List;
import java.util.Optional;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.request.RequestBook;
import com.programmers.app.exception.ActionNotAllowedException;
import com.programmers.app.exception.BookNotFoundException;
import com.programmers.app.validator.Validator;

public class TestBookService implements BookService {
    private final BookRepository bookRepository;

    public TestBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        return null;
    }

    @Override
    public void borrowBook(long bookNumber) {

    }

    @Override
    public void returnBook(long bookNumber) {

    }

    @Override
    public void deleteBook(long bookNumber) {

    }

    @Override
    public void reportLost(long bookNumber) {

    }
}
