package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.data_module.DataManager;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;

import static com.dev_course.library.LibraryMessage.*;

public class LibrarySystem {
    LibraryReader reader;
    LibraryWriter writer;
    DataManager dataManager;
    BookManager bookManager;


    public LibrarySystem(LibraryReader reader, LibraryWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void run() {
        LibraryMode mode = selectMode();

        init(mode);

        selectFunction();

        exit();
    }

    private LibraryMode selectMode() {
        writer.println(MOD_SCREEN.msg());

        String input = reader.read();

        return switch (input) {
            case "0" -> LibraryMode.TEST;
            case "1" -> LibraryMode.NORMAL;
            default -> {
                writer.println(INVALID_MODE.msg());
                yield selectMode();
            }
        };
    }

    private void init(LibraryMode mode) {
        dataManager = mode.getDataManager();
        bookManager = mode.getBookManager();
    }

    private void selectFunction() {
        writer.println(FUNCTION_SCREEN.msg());

        bookManager.updateStates();

        String input = reader.readOrDefault("-1");

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
            default -> writer.println(INVALID_FUNCTION.msg());
        }

        selectFunction();
    }

    private void exit() {
        dataManager.save(bookManager.getBookList());

        writer.println(EXIT.msg());
    }

    private void createBook() {
        writer.println(CREATE_BOOK.msg());

        String title = writeAndRead(READ_CREATE_BOOK_TITLE.msg());
        String author = writeAndRead(READ_CREATE_BOOK_AUTHOR.msg());

        try {
            int pages = Integer.parseInt(writeAndRead(READ_CREATE_BOOK_PAGES.msg()));

            writer.append(bookManager.create(title, author, pages));
        } catch (NumberFormatException e) {
            writer.append(INVALID_INPUT.msg());
        }

        writer.flush();
    }

    private void listBooks() {
        writer.println(LIST_BOOK.msg());

        writer.append(bookManager.getInfo());
        writer.append(END_LIST_BOOK.msg());
        writer.flush();
    }

    private void findBookByTitle() {
        writer.println(FIND_BOOK_BY_TITLE.msg());

        String title = writeAndRead(READ_FIND_BY_TITLE.msg());

        writer.append(bookManager.getInfoByTitle(title));
        writer.append(END_FIND_BOOK_BY_TITLE.msg());
        writer.flush();
    }

    private void rentBookById() {
        writer.println(RENT_BOOK_BY_ID.msg());

        try {
            int id = writeAndReadInt(READ_RENT_BOOK_BY_ID.msg());

            writer.append(bookManager.rentById(id));
        } catch (NumberFormatException e) {
            writer.append(INVALID_INPUT.msg());
        }

        writer.flush();
    }

    private void returnBookById() {
        writer.println(RETURN_BOOK_BY_ID.msg());

        try {
            int id = writeAndReadInt(READ_RETURN_BOOK_BY_ID.msg());

            writer.append(bookManager.returnById(id));
        } catch (NumberFormatException e) {
            writer.append(INVALID_INPUT.msg());
        }

        writer.flush();
    }

    private void lossBookById() {
        writer.println(LOSS_BOOK_BY_ID.msg());

        try {
            int id = writeAndReadInt(READ_LOSS_BOOK_BY_ID.msg());

            writer.append(bookManager.lossById(id));
        } catch (NumberFormatException e) {
            writer.append(INVALID_INPUT.msg());
        }

        writer.flush();
    }

    private void deleteBookById() {
        writer.println(DELETE_BOOK.msg());

        try {
            int id = writeAndReadInt(READ_DELETE_BOOK_ID.msg());

            writer.append(bookManager.deleteById(id));
        } catch (NumberFormatException e) {
            writer.append(INVALID_INPUT.msg());
        }

        writer.flush();
    }

    private String writeAndRead(String msg) {
        writer.println(msg);
        return reader.read();
    }

    private int writeAndReadInt(String msg) {
        return Integer.parseInt(writeAndRead(msg));
    }
}
