package com.programmers.repository;

import com.programmers.domain.Book;
import com.programmers.domain.BookState;

import java.util.List;

public class FileBookRepository implements BookRepository {
    private FileBookRepository() {
    }

    private static class Holder {
        private static final FileBookRepository INSTANCE = new FileBookRepository();
    }

    public static FileBookRepository getInstance() {
        return Holder.INSTANCE;
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
