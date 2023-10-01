package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.io_module.EmptyInputException;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;

import static com.dev_course.library.LibraryMessage.*;
import static java.lang.Integer.parseInt;

public class LibrarySystem {
    private final LibraryReader reader;
    private final LibraryWriter writer;
    private DataManager dataManager;
    private BookManager bookManager;


    public LibrarySystem(LibraryReader reader, LibraryWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        LibraryMode mode = selectMode();

        init(mode);

        try {
            selectFunction();
        } catch (NumberFormatException ne) {
            println(INVALID_INPUT_MESSAGE);
            selectFunction();
        } catch (EmptyInputException e) {
            println(EMPTY_INPUT_MESSAGE);
        }

        exit();
    }

    private void init(LibraryMode mode) {
        dataManager = mode.getDataManager();
        bookManager = mode.getBookManager();

        bookManager.init(dataManager.load());
    }

    private LibraryMode selectMode() {
        println(MOD_SCREEN_MESSAGE);

        String input = readInput();

        return switch (input) {
            case "0" -> LibraryMode.TEST;
            case "1" -> LibraryMode.NORMAL;
            default -> {
                println(INVALID_MODE_MESSAGE);
                yield selectMode();
            }
        };
    }

    private void selectFunction() {
        println(FUNCTION_SCREEN_MESSAGE);

        String input = readInput();

        bookManager.updateStates();

        switch (input) {
            case "0" -> {
                return;
            }
            case "1" -> createBook();
            case "2" -> listBooks();
            case "3" -> findBookByTitle();
            case "4" -> rentBookById();
            case "5" -> returnBookById();
            case "6" -> lossBookById();
            case "7" -> deleteBookById();
            default -> println(INVALID_FUNCTION_MESSAGE);
        }

        selectFunction();
    }

    private void exit() {
        dataManager.save(bookManager.getBookList());

        println(EXIT_MESSAGE);
    }

    private void createBook() {
        println(CREATE_BOOK_MESSAGE);

        String title = printAndReadInput(READ_CREATE_BOOK_TITLE_MESSAGE);
        String author = printAndReadInput(READ_CREATE_BOOK_AUTHOR_MESSAGE);
        int pages = parseInt(printAndReadInput(READ_CREATE_BOOK_PAGES_MESSAGE));

        println(bookManager.create(title, author, pages));
    }

    private void listBooks() {
        println(LIST_BOOK_MESSAGE);

        println(bookManager.getInfo());
        println(END_LIST_BOOK_MESSAGE);
    }

    private void findBookByTitle() {
        println(FIND_BOOK_BY_TITLE_MESSAGE);

        String title = printAndReadInput(READ_FIND_BY_TITLE_MESSAGE);

        println(bookManager.getInfoByTitle(title));
        println(END_FIND_BOOK_BY_TITLE_MESSAGE);
    }

    private void rentBookById() {
        println(RENT_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_RENT_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        println(bookManager.rentById(id));
    }

    private void returnBookById() {
        println(RETURN_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_RETURN_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        println(bookManager.returnById(id));
    }

    private void lossBookById() {
        println(LOSS_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_LOSS_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        println(bookManager.lossById(id));
    }

    private void deleteBookById() {
        println(DELETE_BOOK_MESSAGE);

        String input = printAndReadInput(READ_DELETE_BOOK_ID_MESSAGE);
        int id = parseInt(input);

        println(bookManager.deleteById(id));
    }

    private void println(Object o) {
        writer.println(o);
        writer.println();
    }

    private String readInput() {
        writer.print("> ");

        String input = reader.readLine();

        writer.println();

        return input;
    }

    private String printAndReadInput(LibraryMessage msg) {
        println(msg);
        return readInput();
    }
}
