package com.libraryManagement.util;

import com.libraryManagement.controller.BookController;
import com.libraryManagement.io.BookIO;
import com.libraryManagement.io.BookMenu;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.service.BookService;

// 설정만 할수있도록 고려
public class AppConfig {
    private BookController bookController;

    public void init(Repository repository) {

        //==의존성 주입==//
        BookService bookService = new BookService(repository);
        BookIO bookIO = new BookIO();
        BookMenu bookMenu = new BookMenu();
        this.bookController = new BookController(bookService, bookIO, bookMenu);
    }

    public BookController getBookController() {
        return bookController;
    }
}
