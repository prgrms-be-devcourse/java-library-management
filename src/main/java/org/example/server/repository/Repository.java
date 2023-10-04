package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;

public interface Repository {
    void save(Book book);

    LinkedList<Book> getAll();

    LinkedList<Book> getByName(String name);

    Book findById(int id);

    void delete(int id);
}
