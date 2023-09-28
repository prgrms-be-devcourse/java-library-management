package org.example.server.repository;

import org.example.server.entity.Book;

import java.util.LinkedHashMap;

public class TestBookRepository implements BookRepository {

    private static int bookCount;
    private static LinkedHashMap<Integer, Book> database;

    public TestBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        bookCount = 0;
        database = new LinkedHashMap<>();
    }

    @Override
    public void create(Book book) {
        int bookId = bookCount++;
        book.id = bookId;
        database.put(bookId, book);
        System.out.println("Test: book 등록 했음\n" + book.toString() + database.size());
    }

}
