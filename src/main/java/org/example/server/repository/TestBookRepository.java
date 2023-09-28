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
    }

    @Override
    public String readAll() {
        if (data.isEmpty()) return "존재하는 도서가 없습니다.\n";
        StringBuilder sb = new StringBuilder();
        data.values().forEach((book) -> {
            sb.append(book.toString());
        });
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        data.values().stream().filter(book -> book.name.contains(bookName)).forEach((book) -> {
            sb.append(book.toString());
        });
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        return data.get(bookId);
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }
}
