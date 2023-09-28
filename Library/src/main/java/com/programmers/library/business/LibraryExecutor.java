package com.programmers.library.business;

public class LibraryExecutor implements Runnable{
    private static final int REGISTER_BOOK = 1;
    private static final int FIND_ALL_BOOKS = 2;
    private static final int FIND_BOOK_BY_TITLE =3;
    private static final int RENTAL = 4;
    private static final int RETURN_BOOK = 5;
    private static final int LOST_BOOK = 6;
    private static final int DELETE_BOOK = 7;
    private static final int EXIT = 8;
    private final Menu menu;
    public LibraryExecutor(Menu menu) {
        this.menu = menu;
    }
    @Override
    public void run() {
        while (true){
            int selectMenu = menu.selectMenu();
            boolean continueRun = executeMenu(selectMenu);

            if(!continueRun) break;
        }
    }
    public boolean executeMenu(int selectMenu) {

        switch (selectMenu) {
            case REGISTER_BOOK:
                menu.registerBook();
                return true;
            case FIND_ALL_BOOKS:
                menu.findAllBooks();
                return true;
            case FIND_BOOK_BY_TITLE:
                menu.findBooksByTitle();
                return true;
            case RENTAL:
                menu.rentalBook();
                return true;
            case RETURN_BOOK:
                menu.returnBook();
                return true;
            case LOST_BOOK:
                menu.lostBook();
                return true;
            case DELETE_BOOK:
                menu.deleteBook();
                return true;
            case EXIT:
                menu.exit();
                return false;
            default:
                menu.inValidMenu();
                return true;
        }
    }

}
