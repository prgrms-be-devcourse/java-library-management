package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.List;

public class MemBookRepository implements BookRepository {

    private MemBookRepository() {
    }

    private static class Holder {
        private static final MemBookRepository INSTANCE = new MemBookRepository();
    }

    public static MemBookRepository getInstance() {
        return MemBookRepository.Holder.INSTANCE;
    }

    @Override
    public void addBook(Book Book) {

    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }


    @Override
    public Book findBookById(int id) {
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        return null;
    }

    @Override
    public void updateBookState(int id, BookState bookState) {

    }

    @Override
    public void deleteBook(int id) {

    }
}
