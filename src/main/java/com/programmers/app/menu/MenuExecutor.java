package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;
import com.programmers.app.exception.InvalidInputException;

public class MenuExecutor {
    private final BookController bookController;

    public MenuExecutor(BookController bookController) {
        this.bookController = bookController;
    }

    public void execute(Menu menu) {
        switch (menu) {
            case EXIT:
                bookController.exit();
                break;
            case REGISTER:
                bookController.register(menu);
                break;
            case FIND_ALL_BOOKS:
                bookController.findAllBooks();
                break;
            case SEARCH_TITLE:
                bookController.searchBookByTitle();
                break;
            case BORROW_BOOK:
                bookController.borrowBook(menu);
                break;
            case RETURN_BOOK:
                bookController.returnBook(menu);
                break;
            case REPORT_LOST:
                bookController.reportLostBook(menu);
                break;
            case DELETE_BOOK:
                bookController.deleteBook(menu);
                break;
            default:
                throw new InvalidInputException();
        }
    }
}
