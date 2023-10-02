package com.libraryManagement.repository;

import com.libraryManagement.model.domain.Book;

import java.util.List;

public class MemoryRepository implements Repository {
    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book findOne(String str) {
        return null;
    }

    @Override
    public Book findOne(long id) {
        return null;
    }

    @Override
    public void bookInsert(Book book) {

    }
}
