package com.programmers.view;

import com.programmers.common.Messages;
import com.programmers.domain.Book;
import com.programmers.repository.FileBookRepository;
import com.programmers.repository.MemBookRepository;
import com.programmers.service.BookService;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUI {
    private final Map<Integer, Runnable> functionMap = new HashMap<>();
    private final BookService bookService;

    public ConsoleUI() {
        setMode();
        bookService = BookService.getInstance();
        functionMap.put(1, this::promptForRegisterBook);
        functionMap.put(2, this::displayAllBooks);
        functionMap.put(3, this::promptForSearchBookByTitle);
        functionMap.put(4, this::promptForRent);
        functionMap.put(5, this::promptForReturn);
        functionMap.put(6, this::promptForReportAsLost);
        functionMap.put(7, this::promptForDelete);
    }

    public void run() {
        getCommand();
    }

    public void getCommand() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(Messages.BOOK_MANAGEMENT_FEATURE_MESSAGE);
                functionMap.get(scanner.nextInt()).run();
            } catch (NullPointerException npe) {
                System.out.println(Messages.INVALID_INPUT);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setMode() {
        System.out.print(Messages.MODE_CHOICE_MESSAGE);
        int mode = getIntFromInput();
        if (mode == 1) {
            System.out.println(Messages.NORMAL_MODE_EXECUTION);
            BookService.setBookRepository(FileBookRepository.getInstance());
        } else if (mode == 2) {
            System.out.println(Messages.TEST_MODE_EXECUTION);
            BookService.setBookRepository(MemBookRepository.getInstance());
        } else {
            System.out.println(Messages.INVALID_INPUT);
        }
    }

    public void promptForRegisterBook() {
        System.out.println(Messages.MOVE_TO_BOOK_REGISTER);
        bookService.registerBook(new Book());
        System.out.println(Messages.BOOK_REGISTER_SUCCESS);
    }

    public void displayAllBooks() {
        System.out.println(Messages.BOOK_LIST_START);
        bookService.showAllBooks();
        System.out.println(Messages.BOOK_LIST_FINISH);
    }

    public void promptForSearchBookByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.MOVE_TO_BOOK_SEARCH);
        System.out.print(Messages.BOOK_TITLE_PROMPT);
        bookService.searchBookByTitle(scanner.nextLine());
        System.out.println(Messages.BOOK_SEARCH_FINISH);
    }

    public void promptForRent() {
        System.out.println(Messages.MOVE_TO_BOOK_RENT);
        System.out.print(Messages.BOOK_RENT_PROMPT);
        bookService.rentBook(getIntFromInput());
        System.out.println(Messages.BOOK_RENT_SUCCESS);
    }

    public void promptForReturn() {
        System.out.println(Messages.MOVE_TO_BOOK_RETURN);
        System.out.print(Messages.BOOK_RETURN_PROMPT);
        bookService.returnBook(getIntFromInput());
        System.out.println(Messages.BOOK_RETURN_SUCCESS);
    }

    public void promptForReportAsLost() {
        System.out.println(Messages.MOVE_TO_BOOK_LOST);
        System.out.print(Messages.BOOK_LOST_PROMPT);
        bookService.lostBook(getIntFromInput());
        System.out.println(Messages.BOOK_LOST_SUCCESS);
    }

    public void promptForDelete() {
        System.out.println(Messages.MOVE_TO_BOOK_DELETE);
        System.out.print(Messages.BOOK_DELETE_PROMPT);
        bookService.deleteBook(getIntFromInput());
        System.out.println(Messages.BOOK_DELETE_SUCCESS);

    }

    public int getIntFromInput() {
        int input;
        Scanner scanner = new Scanner(System.in);
        try {
            input = scanner.nextInt();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(Messages.INVALID_INPUT.toString());
        }
        return input;
    }
}
