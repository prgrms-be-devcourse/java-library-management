package client;

import domain.Book;
import repository.FileRepository;
import repository.TestRepository;
import service.Service;

import java.io.IOException;

public class Client {

    private ConsoleManager consoleManager;
    private Service service;

    public Client(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public void run() throws IOException {
        selectMode();
        selectFunction();
    }

    private void selectMode() throws IOException {
        int mode = consoleManager.selectMode();
        switch (mode) {
            case 1 -> {
                service = new Service(new FileRepository());
                System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
            }
            case 2 -> {
                service = new Service(new TestRepository());
                System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
            }
            default -> {
                System.out.println("[System] 다시 선택해주세요\n");
                selectMode();
            }
        }
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
