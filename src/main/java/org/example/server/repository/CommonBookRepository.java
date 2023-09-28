package org.example.server.repository;

import org.example.server.entity.Book;

public class CommonBookRepository implements BookRepository {
    public CommonBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
    }

    @Override
    public void create(Book book) {
        System.out.println("Common: book 등록 했음\n");
    }
}
