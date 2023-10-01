package service;

import domain.Book;
import exception.InputFormatException;
import repository.GeneralRepository;
import repository.Repository;
import view.MessagePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TotalService implements Service{

    private static final Scanner scanner = new Scanner(System.in);

    private final MessagePrinter messagePrinter = new MessagePrinter();

    private final Repository repository;

    private final List<Book> bookList = new ArrayList<>();
    private int id;

    public TotalService(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void load() {
        repository.load(bookList);

        if(bookList.isEmpty()) id = 1;
        else {
            for (Book book : bookList) {
                id = book.getId() + 1;
            }
        }

        mainView();
    }

    @Override
    public void mainView() {
        messagePrinter.printMainView();

        int mode = scanner.nextInt();
        scanner.nextLine();

        switch (mode) {
            case 1 -> registration();
            case 2 -> viewAll();
            case 3 -> findByTitle();
            case 4 -> rentBook();
            case 5 -> returnBook();
            case 6 -> lostBook();
            case 7 -> deleteBook();
            case 8 -> exit();
            default -> throw new InputFormatException("잘못된 입력입니다.");
        }
    }

    @Override
    public void registration() {
        messagePrinter.printRegistrationStart();

        messagePrinter.printRegistrationTitle();
        String title = scanner.nextLine(); // 개행 문자를 읽어내기 위해 추가

        messagePrinter.printRegistrationAuthor();
        String author = scanner.nextLine();

        messagePrinter.printRegistrationPage();
        int page = scanner.nextInt();

        repository.save(id++, title, author, page, bookList);

        messagePrinter.printRegistrationEnd();

        mainView();
    }

    @Override
    public void viewAll() {
        messagePrinter.printViewAllStart();

        StringBuilder stringBuilder = new StringBuilder();

        for (Book book : bookList) {
            // 각 필드를 StringBuilder에 추가
            stringBuilder.append("\n").append(book.toString()).append("\n");
            stringBuilder.append("------------------------------").append("\n");
        }

        System.out.println(stringBuilder);

        messagePrinter.printViewAllEnd();

        mainView();
    }

    @Override
    public void findByTitle() {
        messagePrinter.printFindBookStart();

        messagePrinter.printFindBookTitle();
        String searchTitle = scanner.nextLine();

        List<Book> foundBooks = repository.findByTitle(searchTitle, bookList);

        StringBuilder stringBuilder = new StringBuilder();

        if (foundBooks.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            for (Book book : foundBooks) {
                stringBuilder.append("\n").append(book.toString()).append("\n");
                stringBuilder.append("------------------------------").append("\n");
            }
            System.out.println(stringBuilder);
            System.out.println("[System] 검색된 도서 끝");
        }

        mainView();
    }

    @Override
    public void rentBook() {
        messagePrinter.printRentStart();

        messagePrinter.printRentId();
        int rentId = scanner.nextInt();

        String message = repository.rentById(rentId, bookList);

        messagePrinter.printSystemMessage(message);

        mainView();
    }

    @Override
    public void returnBook() {
        messagePrinter.printReturnStart();

        messagePrinter.printReturnId();
        int returnId = scanner.nextInt();

        String message = repository.returnById(returnId, bookList);

        messagePrinter.printSystemMessage(message);

        mainView();
    }

    @Override
    public void lostBook() {
        messagePrinter.printLostStart();

        messagePrinter.printLostId();
        int lostId = scanner.nextInt();

        String message = repository.lostById(lostId, bookList);

        messagePrinter.printSystemMessage(message);

        mainView();
    }

    @Override
    public void deleteBook() {
        messagePrinter.printDeleteStart();

        messagePrinter.printDeleteId();
        int deleteId = scanner.nextInt();

        String message = repository.deleteById(deleteId, bookList);

        messagePrinter.printSystemMessage(message);

        mainView();
    }



    @Override
    public void exit() {
        messagePrinter.printExitStart();

        System.exit(0);
    }
}
