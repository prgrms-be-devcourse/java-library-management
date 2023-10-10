package org.example.server.service;

import org.example.packet.requestPacket.BookRegisterDto;
import org.example.packet.responsePacket.BookResponseDto;
import org.example.server.entity.Book;
import org.example.server.repository.Repository;

import java.util.LinkedList;

public class BookService implements Service {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(BookRegisterDto bookDto) {
        repository.save(new Book(bookDto));
    }

    public LinkedList<BookResponseDto> readAll() {
        LinkedList<BookResponseDto> bookDtos = new LinkedList<>();
        for (Book book : repository.findAll()) {
            bookDtos.add(new BookResponseDto(book));
        }
        return bookDtos;
    }

    public LinkedList<BookResponseDto> searchAllByName(String name) {
        LinkedList<BookResponseDto> bookDtos = new LinkedList<>();
        for (Book book : repository.findAllByName(name)) {
            bookDtos.add(new BookResponseDto(book));
        }
        return bookDtos;
    }

    public void borrow(int bookId) {
        Book book = repository.getById(bookId);
        book.borrow();
        repository.save(book);
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        book.restore();
        repository.save(book);
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        book.lost();
        repository.save(book);
    }

    public void delete(int bookId) {
        repository.getById(bookId);
        repository.delete(bookId);
    }
}
