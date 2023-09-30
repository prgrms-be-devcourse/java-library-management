package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;

public class MenuExecuter {
    private final BookController bookController;

    public MenuExecuter(BookController bookController) {
        this.bookController = bookController;
    }

    public void execute(Menu menu) {
        menu.executeMenu(bookController);
    }
}
