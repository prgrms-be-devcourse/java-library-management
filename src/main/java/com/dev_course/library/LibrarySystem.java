package com.dev_course.library;

import com.dev_course.book.Book;
import com.dev_course.book.BookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.io_module.EmptyInputException;
import com.dev_course.io_module.Reader;
import com.dev_course.io_module.Writer;

import static com.dev_course.library.LibraryMessage.*;
import static java.lang.Integer.parseInt;

public class LibrarySystem {
    private final Reader reader;
    private final Writer writer;

    private DataManager<Book> dataManager;
    private BookManager bookManager;


    public LibrarySystem(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        ModeFactory mode = selectMode();

        init(mode);

        try {
            selectFunction();
        } catch (NumberFormatException ne) {
            println(INVALID_INPUT.msg());
            selectFunction();
        } catch (EmptyInputException e) {
            println(EMPTY_INPUT.msg());
        }

        exit();
    }

    private void init(ModeFactory mode) {
        dataManager = mode.getDataManager();
        bookManager = mode.getBookManager();

        bookManager.init(dataManager.load());
    }

    private ModeFactory selectMode() {
        println(MOD_SCREEN.msg());

        String input = readInput();

        return switch (input) {
            case "0" -> ModeFactory.TEST;
            case "1" -> ModeFactory.NORMAL;
            default -> {
                println(INVALID_MODE.msg());
                yield selectMode();
            }
        };
    }

    private void selectFunction() {
        println(FUNCTION_SCREEN.msg());

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
            default -> println(INVALID_FUNCTION.msg());
        }

        selectFunction();
    }

    private void exit() {
        dataManager.save(bookManager.getBooks());

        println(EXIT);
    }

    private void createBook() {
        println(CREATE_BOOK);

        String title = printAndReadInput(READ_CREATE_BOOK_TITLE.msg());
        String author = printAndReadInput(READ_CREATE_BOOK_AUTHOR.msg());
        int pages = parseInt(printAndReadInput(READ_CREATE_BOOK_PAGES.msg()));

        println(bookManager.create(title, author, pages));
    }

    private void listBooks() {
        println(LIST_BOOK.msg());

        println(bookManager.getInfos());
        println(END_LIST_BOOK.msg());
    }

    private void findBookByTitle() {
        println(FIND_BOOK_BY_TITLE.msg());

        String title = printAndReadInput(READ_FIND_BY_TITLE.msg());

        println(bookManager.getInfosByTitle(title));
        println(END_FIND_BOOK_BY_TITLE.msg());
    }

    private void rentBookById() {
        println(RENT_BOOK_BY_ID);

        String input = printAndReadInput(READ_RENT_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(bookManager.rentById(id));
    }

    private void returnBookById() {
        println(RETURN_BOOK_BY_ID);

        String input = printAndReadInput(READ_RETURN_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(bookManager.returnById(id));
    }

    private void lossBookById() {
        println(LOSS_BOOK_BY_ID);

        String input = printAndReadInput(READ_LOSS_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(bookManager.lossById(id));
    }

    private void deleteBookById() {
        println(DELETE_BOOK);

        String input = printAndReadInput(READ_DELETE_BOOK_ID.msg());
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

    private String printAndReadInput(String msg) {
        println(msg);
        return readInput();
    }
}
