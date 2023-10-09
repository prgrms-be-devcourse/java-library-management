package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;
import com.programmers.app.exception.InvalidInputException;

public class MenuExecutor {
    private final BookController bookController;

    public MenuExecutor(BookController bookController) {
        this.bookController = bookController;
    }

    public boolean execute(Menu menu) {
        switch (menu) {
            case EXIT:
                bookController.exit();
                return false;
            case REGISTER:
                bookController.register(menu);
                return true;
            case FIND_ALL_BOOKS:
                bookController.findAllBooks();
                return true;
            case SEARCH_TITLE:
                bookController.searchBookByTitle();
                return true;
            case BORROW_BOOK:
                bookController.borrowBook(menu);
                return true;
            case RETURN_BOOK:
                bookController.returnBook(menu);
                return true;
            case REPORT_LOST:
                bookController.reportLostBook(menu);
                return true;
            case DELETE_BOOK:
                bookController.deleteBook(menu);
                return true;
            default:
                throw new InvalidInputException();
        }
    }
}
