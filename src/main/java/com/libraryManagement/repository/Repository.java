package com.libraryManagement.repository;

import com.libraryManagement.model.domain.Book;

import java.util.List;

public interface Repository {

    List<Book> findAll();

    Book findOne(String str);

    Book findOne(long id);

    void bookInsert(Book book);
}
