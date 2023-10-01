package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;
import com.programmers.app.exception.InvalidInputException;

public class MenuExecuter {
    private final BookController bookController;

    public MenuExecuter(BookController bookController) {
        this.bookController = bookController;
    }

    public void execute(Menu menu) {
        switch (menu) {
            case EXIT:
                bookController.exit();
                break;
            case REGISTER:
                bookController.register();
                break;
            case FIND_ALL_BOOKS:
                bookController.findAllBooks();
                break;
            case SEARCH_TITLE:
                bookController.searchBookByTitle();
                break;
            case BORROW_BOOK:
                bookController.borrowBook();
                break;
            case RETURN_BOOK:
                bookController.returnBook();
                break;
            case REPORT_LOST:
                bookController.reportLostBook();
                break;
            case DELETE_BOOK:
                bookController.deleteBook();
                break;
            default:
                throw new InvalidInputException();
        }
    }
}
