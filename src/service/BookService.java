package service;

import common.Messages;
import repository.BookRepository;
import service.command.BookCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookService {
    private final BookRepository bookRepository;
    private Map<Integer, BookCommand> functionMap = new HashMap<>();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        functionMap.put(1, () -> registerBook());
        functionMap.put(2, () -> getAllBooks());
        functionMap.put(3, () -> searchBookByTitle());
        functionMap.put(4, () -> rentBook());
        functionMap.put(5, () -> returnBook());
        functionMap.put(6, () -> lostBook());
        functionMap.put(7, () -> deleteBook());
    }

    public void getCommand() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(Messages.BOOK_MANAGEMENT_FEATURE_MESSAGE.getMessage());
            functionMap.get(scanner.nextInt()).execute();
        }

    }

    public void registerBook() {
        System.out.println(this);
    }

    public void getAllBooks() {
        System.out.println(this);

    }

    public void searchBookByTitle() {
        System.out.println(this);

    }

    public void rentBook() {
        System.out.println(this);


    }

    public void returnBook() {
        System.out.println(this);


    }

    public void lostBook() {
        System.out.println(this);


    }

    public void deleteBook() {
        System.out.println(this);


    }


}
