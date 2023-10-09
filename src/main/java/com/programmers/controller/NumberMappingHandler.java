package com.programmers.controller;

import com.programmers.LibraryEngine;
import com.programmers.exception.ErrorChecking;
import com.programmers.exception.ErrorCode;
import com.programmers.exception.LibraryException;

public class NumberMappingHandler {
    private final BookController bookController;

    public NumberMappingHandler(BookController bookController) {
        this.bookController = bookController;
    }

    public boolean numberMapping(String num) {
        try {
            boolean keep = true;
            int menu = checkingMenu(num);
            keep = executeOption(keep, menu);
            return keep;
        } catch (LibraryException e) {
            retryLibraryEngine(e);
        }
        return true;
    }

    private static void retryLibraryEngine(LibraryException e) {
        System.out.println(e.getErrorCode().getMessage());
        System.out.println("[System] 시스템을 다시 시작합니다.");
        LibraryEngine libraryEngine = new LibraryEngine();
        libraryEngine.init(LibraryEngine.getSetting());
    }

    private int checkingMenu(String num) {
        if (!ErrorChecking.checkNumber(num)) throw new LibraryException(ErrorCode.NOT_NUMBER);
        int menu = changeToNum(num);
        if (!ErrorChecking.optionChecking(menu)) throw new LibraryException(ErrorCode.NUMBER_OVER_BOUNDARY);
        return menu;
    }

    private boolean executeOption(boolean keep, int menu) {
        switch (menu) {
            case 1 -> bookController.enrollBook();
            case 2 -> bookController.findAllBooks();
            case 3 -> bookController.findBookByTitle();
            case 4 -> bookController.rentBook();
            case 5 -> bookController.returnBook();
            case 6 -> bookController.loseBook();
            case 7 -> bookController.deleteBook();
            case 8 -> keep = false;
        }
        return keep;
    }

    private int changeToNum(String input) {
        return Integer.parseInt(input);
    }
}
