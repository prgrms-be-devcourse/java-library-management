package org.example.server.repository;

import org.example.server.entity.Book;

public interface BookRepository {
    void loadData();

    void create(Book book);

    String readAll();
    
    String searchByName(String bookName);
}
