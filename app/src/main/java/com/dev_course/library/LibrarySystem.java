package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.io_module.EmptyInputException;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;

import static com.dev_course.library.LibraryMessage.*;
import static java.lang.Integer.parseInt;

public class LibrarySystem {
    private final LibraryReader reader;
    private final LibraryWriter writer;
    private LibraryMode mode;
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
            writer.println(INVALID_INPUT_MESSAGE);
            selectFunction();
        } catch (EmptyInputException e) {
            writer.println(EMPTY_INPUT_MESSAGE);
        }

        exit();
    }

    private LibraryMode selectMode() {
        writer.println(MOD_SCREEN_MESSAGE);

        String input = readInput();

        return switch (input) {
            case "0" -> LibraryMode.TEST;
            case "1" -> LibraryMode.NORMAL;
            default -> {
                writer.println(INVALID_MODE_MESSAGE);
                yield selectMode();
            }
        };
    }

    private void init(LibraryMode mode) {
        this.mode = mode;
        this.bookManager = mode.getBookManager();

        mode.init();
    }

    private void selectFunction() {
        writer.println(FUNCTION_SCREEN_MESSAGE);

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
            default -> writer.println(INVALID_FUNCTION_MESSAGE);
        }

        selectFunction();
    }

    private void exit() {
        mode.close();

        writer.println(EXIT_MESSAGE);
    }

    private void createBook() {
        writer.println(CREATE_BOOK_MESSAGE);

        String title = printAndReadInput(READ_CREATE_BOOK_TITLE_MESSAGE);
        String author = printAndReadInput(READ_CREATE_BOOK_AUTHOR_MESSAGE);
        int pages = parseInt(printAndReadInput(READ_CREATE_BOOK_PAGES_MESSAGE));

        writer.println(bookManager.create(title, author, pages));
    }

    private void listBooks() {
        writer.println(LIST_BOOK_MESSAGE);

        writer.append(bookManager.getInfo());
        writer.append(END_LIST_BOOK_MESSAGE);
        writer.flush();
    }

    private void findBookByTitle() {
        writer.println(FIND_BOOK_BY_TITLE_MESSAGE);

        String title = printAndReadInput(READ_FIND_BY_TITLE_MESSAGE);

        writer.append(bookManager.getInfoByTitle(title));
        writer.append(END_FIND_BOOK_BY_TITLE_MESSAGE);
        writer.flush();
    }

    private void rentBookById() {
        writer.println(RENT_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_RENT_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        writer.println(bookManager.rentById(id));
    }

    private void returnBookById() {
        writer.println(RETURN_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_RETURN_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        writer.println(bookManager.returnById(id));
    }

    private void lossBookById() {
        writer.println(LOSS_BOOK_BY_ID_MESSAGE);

        String input = printAndReadInput(READ_LOSS_BOOK_BY_ID_MESSAGE);
        int id = parseInt(input);

        writer.println(bookManager.lossById(id));
    }

    private void deleteBookById() {
        writer.println(DELETE_BOOK_MESSAGE);

        String input = printAndReadInput(READ_DELETE_BOOK_ID_MESSAGE);
        int id = parseInt(input);

        writer.println(bookManager.deleteById(id));
    }

    private String readInput() {
        writer.print("> ");
        return reader.readLine();
    }

    private String printAndReadInput(LibraryMessage msg) {
        writer.println(msg);
        return readInput();
    }
}
