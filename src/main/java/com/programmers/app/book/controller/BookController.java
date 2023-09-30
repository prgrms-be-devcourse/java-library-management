package com.programmers.app.book.controller;

public interface BookController {

    void exit();

    void register();

    void findAllBooks();

    void searchBookByTitle();

    void borrowBook();

    void returnBook();

    void reportLostBook();

    void deleteBook();
}
