package com.example.library.repository;

import com.example.library.domain.Book;

import java.util.Set;


public interface BookRepository {
    void addBook(Book book);
    void printAll();

    void printByTitle(String bookName);

    boolean borrowBook(int bookId);

    boolean returnBook(int bookId);

    boolean loseBook(int bookId);

    boolean deleteBook(int bookId);

    void arrangeBookStatus();

    void saveBookList();

    int size();

    boolean isContainsKey(long number);
}
