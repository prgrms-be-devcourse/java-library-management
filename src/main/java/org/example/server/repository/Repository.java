package org.example.server.repository;

import org.example.server.entity.Book;

import java.util.LinkedList;

public interface Repository {
    void save(Book book);

    LinkedList<Book> findAll();

    LinkedList<Book> findAllByName(String name);

    Book getById(int id);

    void delete(int id);
}
