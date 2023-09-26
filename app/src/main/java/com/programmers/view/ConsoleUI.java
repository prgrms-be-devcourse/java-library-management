package com.programmers.view;

import com.programmers.common.Messages;
import com.programmers.factory.BookServiceFactory;
import com.programmers.service.BookService;

import java.util.Scanner;

public class ConsoleUI {


    public void run() {
        BookServiceFactory.setMode(getMode());
        BookService bookService = BookServiceFactory.getBookService();
        bookService.getCommand();
    }

    public int getMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Messages.MODE_CHOICE_MESSAGE.getMessage());
        return scanner.nextInt();
    }
}
