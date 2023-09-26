package com.programmers.view;

import com.programmers.common.Messages;
import com.programmers.repository.BookRepository;
import com.programmers.repository.FileBookRepository;
import com.programmers.repository.MemBookRepository;
import com.programmers.service.BookService;
import com.programmers.view.command.BookCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private BookRepository bookRepository;
    private Map<Integer, BookCommand> functionMap = new HashMap<>();
    private final BookService bookService = BookService.getInstance();

    public ConsoleUI() {
        functionMap.put(1, bookService::registerBook);
        functionMap.put(2, bookService::getAllBooks);
        functionMap.put(3, bookService::searchBookByTitle);
        functionMap.put(4, bookService::rentBook);
        functionMap.put(5, bookService::returnBook);
        functionMap.put(6, bookService::lostBook);
        functionMap.put(7, bookService::deleteBook);
    }

    public void run() {
        setMode();
        getCommand();
    }

    public void getCommand() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(Messages.BOOK_MANAGEMENT_FEATURE_MESSAGE.getMessage());
                functionMap.get(scanner.nextInt()).execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Messages.MODE_CHOICE_MESSAGE.getMessage());
        int mode = scanner.nextInt();
        if (mode == 1) {
            BookService.setBookRepository(FileBookRepository.getInstance());
        } else if (mode == 2) {
            BookService.setBookRepository(MemBookRepository.getInstance());
        } else {
            System.out.println(Messages.INVALID_INPUT);
        }
    }
}
