package org.example.server.service;

import org.example.packet.dto.BookDto;

import java.util.LinkedList;

public interface Service {
    void register(BookDto bookDto);

    LinkedList<BookDto> readAll();

    LinkedList<BookDto> searchByName(String name);

    void borrow(int bookId);

    void restore(int bookId);

    void lost(int bookId);

    void delete(int bookId);
}
