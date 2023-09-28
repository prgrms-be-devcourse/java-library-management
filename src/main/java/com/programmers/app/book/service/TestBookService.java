package com.programmers.app.book.service;

import java.util.Map;

import com.programmers.app.book.domain.Book;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.request.RequestBook;

public class TestBookService implements BookService {
    private final BookRepository bookRepository;

    public TestBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void register(RequestBook requestBook) {
        long newBookNumber = bookRepository.getLastBookNumber();
        bookRepository.add(requestBook.toBook(newBookNumber));
    }

    @Override
    public Map<Integer, Book> findAllBooks() {
        return null;
    }

    @Override
    public Book findByTitle(String title) {
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
