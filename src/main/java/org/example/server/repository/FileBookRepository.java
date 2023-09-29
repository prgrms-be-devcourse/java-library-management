package org.example.server.repository;

import org.example.server.entity.Book;

import java.util.LinkedHashMap;

public class FileBookRepository implements BookRepository {
    private static int count;
    private static LinkedHashMap<Integer, Book> data;


    public FileBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void create(Book book) {
        System.out.println("Common: book 등록 했음\n");
    }

    @Override
    public String readAll() {
        return null;
    }

    @Override
    public String searchByName(String bookName) {
        return null;
    }

    @Override
    public Book getById(int bookId) {
        return null;
    }

    @Override
    public void delete(int bookId) {

    }
}
