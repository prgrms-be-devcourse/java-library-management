package com.libraryManagement.repository;

import com.libraryManagement.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements Repository {
    private List<Book> memoryBookList;

    public MemoryRepository() {
        memoryBookList = new ArrayList<>();
    }

    @Override
    public List<Book> findAllBooks() {
        return null;
    }

    @Override
    public List<Book> findBooksByTitle(String str) {
        return null;
    }

    @Override
    public Book findBookById(long id) {
        return null;
    }

    @Override
    public void insertBook(Book book) {
        memoryBookList.add(book);
    }

    @Override
    public void updateBookStatus(long id, String bookStatus) {

    }

    @Override
    public long getNumCreatedBooks() {
        return 0;
    }

}
