package com.programmers.view;

import com.programmers.common.ErrorMessages;
import com.programmers.common.InfoMessages;
import com.programmers.common.PromptMessages;
import com.programmers.domain.Book;
import com.programmers.repository.FileBookRepository;
import com.programmers.repository.MemBookRepository;
import com.programmers.service.BookService;

import java.util.*;

public class ConsoleUI {

    private static final int NORMAL_MODE = 1;
    private static final int TEST_MODE = 2;
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
                System.out.print(PromptMessages.BOOK_MANAGEMENT_FEATURE_MESSAGE.getMessage());
                functionMap.get(scanner.nextInt()).run();
            } catch (NullPointerException | InputMismatchException e) {
                System.out.println(ErrorMessages.INVALID_INPUT.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setMode() {
        System.out.print(PromptMessages.MODE_CHOICE_MESSAGE.getMessage());
        int mode = getIntFromInput();
        if (mode == NORMAL_MODE) {
            System.out.println(InfoMessages.NORMAL_MODE_EXECUTION.getMessage());
            BookService.setBookRepository(FileBookRepository.getInstance());
        } else if (mode == TEST_MODE) {
            System.out.println(InfoMessages.TEST_MODE_EXECUTION.getMessage());
            BookService.setBookRepository(MemBookRepository.getInstance());
        } else {
            System.out.println(ErrorMessages.INVALID_INPUT.getMessage());
        }
    }

    public void promptForRegisterBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(PromptMessages.BOOK_REGISTER_TITLE_PROMPT.getMessage());
        String title = scanner.nextLine();
        System.out.print(PromptMessages.BOOK_REGISTER_AUTHOR_PROMPT.getMessage());
        String author = scanner.nextLine();
        System.out.print(PromptMessages.BOOK_REGISTER_PAGES_PROMPT.getMessage());
        int pages = scanner.nextInt();

        System.out.println(InfoMessages.MOVE_TO_BOOK_REGISTER.getMessage());
        bookService.registerBook(new Book(title, author, pages));
        System.out.println(InfoMessages.BOOK_REGISTER_SUCCESS.getMessage());
    }

    public void displayAllBooks() {
        System.out.println(InfoMessages.BOOK_LIST_START.getMessage());
        bookService.getAllBooks().forEach(book -> {
            System.out.println(book);
            System.out.println("-----------------------" + System.lineSeparator());
        });
        System.out.println(InfoMessages.BOOK_LIST_FINISH.getMessage());
    }

    public void promptForSearchBookByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(InfoMessages.MOVE_TO_BOOK_SEARCH.getMessage());
        System.out.print(PromptMessages.BOOK_TITLE_SEARCH_PROMPT.getMessage());
        bookService.searchBookByTitle(scanner.nextLine());
        System.out.println(InfoMessages.BOOK_SEARCH_FINISH.getMessage());
    }

    public void promptForRent() {
        System.out.println(InfoMessages.MOVE_TO_BOOK_RENT.getMessage());
        System.out.print(PromptMessages.BOOK_RENT_PROMPT.getMessage());
        bookService.rentBook(getIntFromInput());
        System.out.println(InfoMessages.BOOK_RENT_SUCCESS.getMessage());
    }

    public void promptForReturn() {
        System.out.println(InfoMessages.MOVE_TO_BOOK_RETURN.getMessage());
        System.out.print(PromptMessages.BOOK_RETURN_PROMPT.getMessage());
        bookService.returnBook(getIntFromInput());
        System.out.println(InfoMessages.BOOK_RETURN_SUCCESS.getMessage());
    }

    public void promptForReportAsLost() {
        System.out.println(InfoMessages.MOVE_TO_BOOK_LOST.getMessage());
        System.out.print(PromptMessages.BOOK_LOST_PROMPT.getMessage());
        bookService.lostBook(getIntFromInput());
        System.out.println(InfoMessages.BOOK_LOST_SUCCESS.getMessage());
    }

    public void promptForDelete() {
        System.out.println(InfoMessages.MOVE_TO_BOOK_DELETE.getMessage());
        System.out.print(PromptMessages.BOOK_DELETE_PROMPT.getMessage());
        bookService.deleteBook(getIntFromInput());
        System.out.println(InfoMessages.BOOK_DELETE_SUCCESS.getMessage());

    }

    public int getIntFromInput() {
        int input;
        Scanner scanner = new Scanner(System.in);
        try {
            input = scanner.nextInt();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(ErrorMessages.INVALID_INPUT.getMessage());
        }
        return input;
    }
}
