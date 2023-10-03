package client;

import domain.Book;
import repository.FileRepository;
import repository.TestRepository;
import service.Service;

import java.io.IOException;

public class Client {

    private final ConsoleManager consoleManager;
    private Service service;

    public Client(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public void run() throws IOException {
        selectMode();
        selectFunction();
    }

    private void selectMode() throws IOException {
        int mode = consoleManager.modeSelect();
        switch (mode) {
            case 1 -> {
                service = new Service(new FileRepository());
            }
            case 2 -> {
                service = new Service(new TestRepository());
            }
            default -> {
                selectMode();
            }
        }
        consoleManager.modePrint(mode);
    }

    private void selectFunction() throws IOException {
        int function = consoleManager.selectFunction();
        switch (function) {
            case 1 -> {
                Book book = consoleManager.addBook();
                service.addBook(book);
                consoleManager.addBookResult();
            }
            case 2 -> {
                consoleManager.getAll(service.getAll());
            }
            case 3 -> {
                String keyword = consoleManager.searchName();
                consoleManager.searchNamePrint(service.searchName(keyword));
            }
            case 4 -> {
                int i = consoleManager.rentalBook();
                service.rentalBook(i);
            }
            case 5 -> {
                int i = consoleManager.returnBook();
                service.organizeBook(i);
            }
            case 6 -> {
                int i = consoleManager.lostBook();
                service.lostBook(i);
            }
            case 7 -> {
                int i = consoleManager.deleteBook();
                service.deleteBook(i);
            }
            default -> {return;}
        }
        selectFunction();
    }
}
