package com.programmers.app.menu;

import java.util.function.Consumer;

import com.programmers.app.book.controller.BookController;

public enum Menu {
    EXIT(0, BookController::exit),
    REGISTER(1, BookController::register),
    FIND_ALL_BOOKS(2, BookController::findAllBooks),
    SEARCH_TITLE(3, BookController::searchBookByTitle),
    BORROW_BOOK(4, BookController::borrowBook),
    RETURN_BOOK(5, BookController::returnBook),
    REPORT_LOST(6, BookController::reportLostBook),
    DELETE_BOOK(7, BookController::deleteBook);

    private final int menuCode;
    private final Consumer<BookController> functionToExecute;

    Menu(int menuCode, Consumer<BookController> functionToExecute) {
        this.menuCode = menuCode;
        this.functionToExecute = functionToExecute;
    }

    public boolean isSelected(int menuCode) {
        return this.menuCode == menuCode;
    }

    public void executeMenu(BookController bookController) {
        this.functionToExecute.accept(bookController);
    }
}
