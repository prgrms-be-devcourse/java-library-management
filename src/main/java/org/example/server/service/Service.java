package org.example.server.service;

import org.example.packet.BookRegisterDto;
import org.example.packet.BookResponseDto;

import java.util.LinkedList;

public interface Service {
    void register(BookRegisterDto bookDto);

    LinkedList<BookResponseDto> readAll();

    LinkedList<BookResponseDto> searchAllByName(String name);

    void borrow(int bookId);

    void restore(int bookId);

    void lost(int bookId);

    void delete(int bookId);
}
