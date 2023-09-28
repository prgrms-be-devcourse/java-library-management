package org.example.server.repository;

import org.example.server.entity.Book;

import java.util.LinkedHashMap;

public class TestBookRepository implements BookRepository {

    private static int count;
    private static LinkedHashMap<Integer, Book> data;

    public TestBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        count = 0;
        data = new LinkedHashMap<>();
    }

    @Override
    public void create(Book book) {
        int bookId = count++;
        book.id = bookId;
        data.put(bookId, book);
        System.out.println("Test: book 등록 했음\n" + book.toString() + data.size());
    }
}
