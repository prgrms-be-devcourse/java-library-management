package com.programmers.app.book.controller;

import com.programmers.app.menu.Menu;

public interface BookController {

    void exit();

    void register(Menu menu);

    void findAllBooks();

    void searchBookByTitle();

    void borrowBook(Menu menu);

    void returnBook(Menu menu);

    void reportLostBook(Menu menu);

    void deleteBook(Menu menu);
}
