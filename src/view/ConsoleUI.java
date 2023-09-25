package view;

import common.Messages;
import factory.BookServiceFactory;
import service.BookService;

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
