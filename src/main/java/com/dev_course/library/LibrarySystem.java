package com.dev_course.library;

import com.dev_course.io_module.EmptyInputException;
import com.dev_course.io_module.Reader;
import com.dev_course.io_module.Writer;

import java.util.NoSuchElementException;

import static com.dev_course.library.LibraryMessage.*;
import static java.lang.Integer.parseInt;

public class LibrarySystem {
    private final Reader reader;
    private final Writer writer;

    private LibraryService libraryService;


    public LibrarySystem(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        try {
            ModeFactory mode = selectMode();

            init(mode);

            selectFunction();

            libraryService.close();
        } catch (EmptyInputException | NullPointerException e) {
            println(EMPTY_INPUT.msg());
        }

        println(EXIT.msg());
    }

    private void init(ModeFactory mode) {
        libraryService = mode.getLibraryService();
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

        libraryService.updateBooks();

        try {
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
        } catch (NumberFormatException nf) {
            println(INVALID_INPUT.msg());
        } catch (NoSuchElementException nse) {
            println(NOT_EXIST_ID.msg());
        }

        selectFunction();
    }

    private void createBook() {
        println(CREATE_BOOK.msg());

        String title = printAndReadInput(READ_CREATE_BOOK_TITLE.msg());
        String author = printAndReadInput(READ_CREATE_BOOK_AUTHOR.msg());
        int pages = parseInt(printAndReadInput(READ_CREATE_BOOK_PAGES.msg()));

        println(libraryService.createBook(title, author, pages));
    }

    private void listBooks() {
        println(LIST_BOOK.msg());
        println(libraryService.bookInfos());
        println(END_LIST_BOOK.msg());
    }

    private void findBookByTitle() {
        println(FIND_BOOK_BY_TITLE.msg());

        String title = printAndReadInput(READ_FIND_BY_TITLE.msg());

        println(libraryService.findBooksByTitle(title));
        println(END_FIND_BOOK_BY_TITLE.msg());
    }

    private void rentBookById() {
        println(RENT_BOOK_BY_ID.msg());

        String input = printAndReadInput(READ_RENT_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(libraryService.rentBookById(id));
    }

    private void returnBookById() {
        println(RETURN_BOOK_BY_ID.msg());

        String input = printAndReadInput(READ_RETURN_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(libraryService.returnBookById(id));
    }

    private void lossBookById() {
        println(LOSS_BOOK_BY_ID.msg());

        String input = printAndReadInput(READ_LOSS_BOOK_BY_ID.msg());
        int id = parseInt(input);

        println(libraryService.lossBookById(id));
    }

    private void deleteBookById() {
        println(DELETE_BOOK.msg());

        String input = printAndReadInput(READ_DELETE_BOOK_ID.msg());
        int id = parseInt(input);

        println(libraryService.deleteBookById(id));
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
