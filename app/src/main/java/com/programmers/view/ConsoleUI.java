package com.programmers.view;

import com.programmers.common.Messages;
import com.programmers.repository.FileBookRepository;
import com.programmers.repository.MemBookRepository;
import com.programmers.service.BookService;
import com.programmers.view.command.BookCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private final Map<Integer, BookCommand> functionMap = new HashMap<>();

    public ConsoleUI() {
        setMode();
        BookService bookService = BookService.getInstance();
        functionMap.put(1, bookService::registerBook);
        functionMap.put(2, bookService::getAllBooks);
        functionMap.put(3, bookService::searchBookByTitle);
        functionMap.put(4, bookService::rentBook);
        functionMap.put(5, bookService::returnBook);
        functionMap.put(6, bookService::lostBook);
        functionMap.put(7, bookService::deleteBook);
    }

    public void run() {
        getCommand();
    }

    public void getCommand() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(Messages.BOOK_MANAGEMENT_FEATURE_MESSAGE);
                functionMap.get(scanner.nextInt()).execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Messages.MODE_CHOICE_MESSAGE);
        int mode = scanner.nextInt();
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
}
