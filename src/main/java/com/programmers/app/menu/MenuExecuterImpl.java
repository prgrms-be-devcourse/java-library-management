package com.programmers.app.menu;

import com.programmers.app.book.controller.BookController;

public class MenuExecuterImpl implements MenuExecuter {
    private final BookController bookController;

    public MenuExecuterImpl(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
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
