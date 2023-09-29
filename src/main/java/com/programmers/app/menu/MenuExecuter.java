package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;

public class MenuExecuter {
    private final BookController bookController;

    public MenuExecuter(BookController bookController) {
        this.bookController = bookController;
    }

    public void execute(Menu menu) {
            switch (menu) {
                case REGISTER:
                    bookController.register();
                    break;
                case REFERNCEALL:
                    bookController.findAllBooks();
                    break;
                case SEARCHTITLE:
                    bookController.searchBookByTitle();
                    break;
                case BORROW:
                    bookController.borrowBook();
                    break;
                case RETURN:
                    bookController.returnBook();
                    break;
                case REPORTLOST:
                    bookController.reportLostBook();
                    break;
                case DELETE:
                    bookController.deleteBook();
                    break;
                case EXIT:
                    System.exit(0);
            }
    }
}
