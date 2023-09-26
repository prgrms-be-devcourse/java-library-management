package com.dev_course.library;

import com.dev_course.book.BookManager;
import com.dev_course.io_module.LibraryReader;
import com.dev_course.io_module.LibraryWriter;

import java.io.IOException;

import static com.dev_course.library.LibraryMessage.*;

public class LibrarySystem {
    LibraryReader reader;
    LibraryWriter writer;
    BookManager bookManager;


    public LibrarySystem(LibraryReader reader, LibraryWriter writer, BookManager bookManager) {
        this.reader = reader;
        this.writer = writer;
        this.bookManager = bookManager;
    }

    public void run() {
        setMode();

        selectFunction();

        exit();
    }

    private void setMode() {
        writer.println(MOD_SCREEN.msg());

        // TODO : SystemMode 따른 도서관 앱의 로드, 저장 기능 변경
    }

    private void selectFunction() {
        writer.println(FUNCTION_SCREEN.msg());

        String input = reader.readOrElse("-1");

        switch (input) {
            case "0" -> {
                return;
            }
            case "1" -> createBook();
            case "2" -> listBooks();
            case "3" -> findBookByTitle();
            default -> writer.println(INVALID_FUNCTION.msg());
        }

        selectFunction();
    }

    private void exit() {
        writer.println(EXIT.msg());
    }

    private void createBook() {
        writer.println(CREATE_BOOK.msg());

        String resMsg;

        try {
            String title = reader.read();
            String author = reader.read();
            int pages = Integer.parseInt(reader.read());

            resMsg = bookManager.create(title, author, pages);
        } catch (IOException | NumberFormatException e) {
            resMsg = INVALID_INPUT.msg();
        }

        writer.println(resMsg);
    }

    private void listBooks() {
        writer.println(LIST_BOOK.msg());
    }

    private void findBookByTitle() {
        writer.println(FIND_BOOK_BY_TITLE.msg());
    }
}
